package com.goodsoft.loggingconsumer.configuration;

import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import com.goodsoft.infra.modulecore.logging.models.LoggingConfiguration;
import com.goodsoft.loggingconsumer.logic.LogRecordConsumer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

public class ApplicationConfiguration
{
    @Bean
    Queue queue(String queueName)
    {
        return new Queue(queueName, false);
    }

    //create MessageListenerContainer using default connection factory
    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter, IConfigurationManager<LoggingConfiguration> loggingConfigurationManager)
    {
        var queueName = "logging-record-created-queue";
        var configuration = loggingConfigurationManager.getConfiguration();
        var messageBroker = configuration.getMessageBroker();
        if(messageBroker != null)
        {
            var routingItem = messageBroker.findMessageRoutingByName("recordcreated");
            if(routingItem != null)
            {
                queueName = routingItem.getQueue();
            }
        }

        var listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(queue(queueName));
        listenerContainer.setMessageListener(listenerAdapter);

        return listenerContainer;

    }

    @Bean
    MessageListenerAdapter listenerAdapter(LogRecordConsumer receiver, MessageConverter messageConverter) {
        var adapter = new MessageListenerAdapter(receiver, "receiveMessage");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        var converter = new Jackson2JsonMessageConverter();
        converter.setAlwaysConvertToInferredType(false);
        return converter;
    }
}
