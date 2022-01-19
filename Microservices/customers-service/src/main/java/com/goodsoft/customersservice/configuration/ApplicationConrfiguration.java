package com.goodsoft.customersservice.configuration;

import com.goodsoft.infra.mediator.factory.IPipelineFactory;
import com.goodsoft.infra.mediator.factory.PipelineFactory;
import com.goodsoft.infra.modulecore.configuration.MessageBrokerConfiguration;
import com.goodsoft.infra.modulecore.messaging.abstractions.IMessageProducer;
import com.goodsoft.infra.modulecore.messaging.rabbitmq.RabbitMQMessageProducer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class ApplicationConrfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory  rabbitMQConnectionFactory(MessageBrokerConfiguration messageBrokerConfiguration) throws Exception {

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
    public IPipelineFactory pipelineFactory()
    {
        return new PipelineFactory();
    }

    @Bean
    public CustomerServiceConfiguration configuration(ICustomersConfigurationManager customersConfigurationManager)
    {
        return customersConfigurationManager.getConfiguration();
    }

    @Bean
    public MessageBrokerConfiguration messageBrokerConfiguration(CustomerServiceConfiguration configuration) throws Exception {

        var messageBrokerConfiguration = configuration.getMessageBroker();
        if(messageBrokerConfiguration == null)
        {
            throw new Exception("Unable to create RabbitMQ configuration factory. Message broker configuration not found");
        }

        return messageBrokerConfiguration;
    }
}
