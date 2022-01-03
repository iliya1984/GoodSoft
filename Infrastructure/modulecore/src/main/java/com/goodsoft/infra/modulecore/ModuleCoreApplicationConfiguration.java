package com.goodsoft.infra.modulecore;

import com.goodsoft.infra.modulecore.logging.models.RequestMetadata;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import com.goodsoft.infra.modulecore.logging.models.LoggingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
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
}