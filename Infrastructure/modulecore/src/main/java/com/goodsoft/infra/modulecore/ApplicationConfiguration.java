package com.goodsoft.infra.modulecore;

import com.goodsoft.infra.modulecore.configuration.HttpContextConfiguration;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import com.goodsoft.infra.modulecore.logging.abstractions.ILoggerMessageProducer;
import com.goodsoft.infra.modulecore.logging.implementations.AsyncLogger;
import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import com.goodsoft.infra.modulecore.logging.implementations.LoggerMessageProducer;
import com.goodsoft.infra.modulecore.logging.models.LoggingConfiguration;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.RequestHeader;

@Configuration
public class ApplicationConfiguration
{
    @Bean
    @Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public HttpContextConfiguration httpContextConfiguration(@RequestHeader(value="GS-CorrelationId") String correlationIdHeader ) {

        var configuration = new HttpContextConfiguration();
        configuration.setCorrelationId(correlationIdHeader);

        return configuration;
    }

    @Bean
    public LoggingConfiguration loggingConfiguration(IConfigurationManager<LoggingConfiguration> configurationManager)
    {
        return configurationManager.getConfiguration();
    }
}