package com.goodsoft.infra.modulecore;

import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import com.goodsoft.infra.modulecore.logging.abstractions.ILoggerMessageProducer;
import com.goodsoft.infra.modulecore.logging.implementations.AsyncLogger;
import com.goodsoft.infra.modulecore.logging.models.RequestMetadata;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import com.goodsoft.infra.modulecore.logging.models.LoggingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class ModuleCoreApplicationConfiguration
{
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