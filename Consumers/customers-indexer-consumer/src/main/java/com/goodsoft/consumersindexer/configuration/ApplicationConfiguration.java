package com.goodsoft.consumersindexer.configuration;

import com.goodsoft.consumersindexer.listeners.CustomerCreatedEventReceiver;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.baeldung.spring.data.es.repository")
@ComponentScan(basePackages = { "com.baeldung.spring.data.es.service" })
public class ApplicationConfiguration
{

    @Bean
    Queue queue() {
        return new Queue("customers-customer-created-queue", false);
    }

    //create MessageListenerContainer using default connection factory
    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter)
    {
        var listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(queue());
        listenerContainer.setMessageListener(listenerAdapter);

        return listenerContainer;

    }

    @Bean
    MessageListenerAdapter listenerAdapter(CustomerCreatedEventReceiver receiver, MessageConverter messageConverter) {
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

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
