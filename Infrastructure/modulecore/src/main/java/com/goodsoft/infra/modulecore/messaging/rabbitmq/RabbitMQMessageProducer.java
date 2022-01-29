package com.goodsoft.infra.modulecore.messaging.rabbitmq;

import com.goodsoft.infra.modulecore.configuration.MessageBrokerConfiguration;
import com.goodsoft.infra.modulecore.messaging.abstractions.IMessageProducer;
import com.goodsoft.infra.modulecore.messaging.entities.Message;
import com.goodsoft.infra.modulecore.messaging.entities.MessageSendResult;
import com.goodsoft.infra.modulecore.messaging.enums.MessageSendStatusCode;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;

import javax.swing.text.html.parser.Entity;
import java.util.UUID;

public class RabbitMQMessageProducer implements IMessageProducer
{
    private AmqpTemplate _rabbitMQTemplate;
    private MessageBrokerConfiguration _configuration;

    public RabbitMQMessageProducer(AmqpTemplate rabbitMQTemplate, MessageBrokerConfiguration configuration)
    {
        _rabbitMQTemplate = rabbitMQTemplate;
        _configuration = configuration;
    }

    @SneakyThrows
    public <T> MessageSendResult send(Message message)
    {
        var result = new MessageSendResult();

        var messageId = UUID.randomUUID().toString();

        result.setMessageId(messageId);
        result.setStatus(MessageSendStatusCode.Failure);

        var metadata = message.getMetadata();

        if(metadata == null)
        {
            throw new Exception("Unable to send message, message metadata was not set");
        }

        var action = metadata.getAction();
        if(action == null || action == "")
        {
            throw new Exception("Unable to send message, message action was not set");
        }

        var messageRouting = _configuration.findMessageRoutingByName(metadata.getAction());
        if(messageRouting != null)
        {
            var exchange = messageRouting.getExchange();
            var routingKey = messageRouting.getRoutingKey();
            _rabbitMQTemplate.convertAndSend(exchange, routingKey, message);
            result.setStatus(MessageSendStatusCode.Success);
        }

        return result;
    }
}
