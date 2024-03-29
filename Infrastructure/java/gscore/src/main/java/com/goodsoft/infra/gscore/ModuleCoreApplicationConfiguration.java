package com.goodsoft.infra.gscore;

import com.goodsoft.infra.gscore.configuration.MessageBrokerConfiguration;
import com.goodsoft.infra.gscore.logging.abstractions.ILogger;
import com.goodsoft.infra.gscore.logging.abstractions.ILoggerMessageProducer;
import com.goodsoft.infra.gscore.logging.implementations.AsyncLogger;
import com.goodsoft.infra.gscore.configuration.IConfigurationManager;
import com.goodsoft.infra.gscore.logging.models.LoggingConfiguration;
import com.goodsoft.infra.gscore.messaging.abstractions.IMessageProducer;
import com.goodsoft.infra.gscore.messaging.rabbitmq.RabbitMQMessageProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModuleCoreApplicationConfiguration
{
    @Bean
    public MessageConverter jsonMessageConverter() {

        var converter = new Jackson2JsonMessageConverter();
        converter.setAlwaysConvertToInferredType(true);

        return converter;
    }

    @Bean
    public ConnectionFactory rabbitMQConnectionFactory(MessageBrokerConfiguration messageBrokerConfiguration) throws Exception {

        var connectionString = messageBrokerConfiguration.getConnectionString();
        if(messageBrokerConfiguration == null)
        {
            throw new Exception("Unable to create RabbitMQ configuration factory. Message broker connection string not found");
        }

        return new CachingConnectionFactory(connectionString);
    }

    @Bean
    public IMessageProducer messageProducer(AmqpTemplate rabbitMQTemplate, MessageBrokerConfiguration messageBrokerConfiguration)
    {
        return new RabbitMQMessageProducer(rabbitMQTemplate, messageBrokerConfiguration);
    }

    @Bean
    public AmqpTemplate rabbitMQTemplate(ConnectionFactory rabbitMQConnectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitMQConnectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public LoggingConfiguration loggingConfiguration(IConfigurationManager<LoggingConfiguration> configurationManager)
    {
        return configurationManager.getConfiguration();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ILogger logger(ILoggerMessageProducer producer, IConfigurationManager<LoggingConfiguration> loggingConfigurationManager)
    {
        return new AsyncLogger(producer, loggingConfigurationManager);
    }
}