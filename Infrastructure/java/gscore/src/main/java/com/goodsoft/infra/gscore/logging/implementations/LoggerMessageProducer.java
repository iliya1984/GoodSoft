package com.goodsoft.infra.gscore.logging.implementations;

import com.goodsoft.infra.gscore.configuration.IConfigurationManager;
import com.goodsoft.infra.gscore.logging.abstractions.ILoggerMessageProducer;
import com.goodsoft.infra.gscore.logging.models.LogRecord;
import com.goodsoft.infra.gscore.logging.models.LogRecordMessage;
import com.goodsoft.infra.gscore.logging.models.LoggingConfiguration;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoggerMessageProducer implements ILoggerMessageProducer
{
    private final String Action = "recordcreated";

    private AmqpTemplate _rabbitTemplate;
    private LoggingConfiguration _configuration;

    public LoggerMessageProducer(IConfigurationManager<LoggingConfiguration> loggingConfigurationManager, AmqpTemplate rabbitMQTemplate)
    {
        _rabbitTemplate = rabbitMQTemplate;
        _configuration = loggingConfigurationManager.getConfiguration();
    }

    public void notifyRecordCreated(LogRecord record)
    {
        var message = new LogRecordMessage();
        message.setMetadata(record.getMetadata());
        message.setMessage(record.getMessage());
        message.setCode(record.getCode());
        message.setLevel(record.getLevel());

        var messageBrokerConfiguration = _configuration.getMessageBroker();
        if(messageBrokerConfiguration != null)
        {
            var messageRouting = messageBrokerConfiguration.findMessageRoutingByName(Action);
            if(messageRouting != null)
            {
                var exchange = messageRouting.getExchange();
                var routingKey = messageRouting.getRoutingKey();
                _rabbitTemplate.convertAndSend(exchange, routingKey, message);
            }
        }
    }
}
