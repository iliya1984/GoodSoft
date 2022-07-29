package com.goodsoft.infra.microservice;

import com.goodsoft.infra.modulecore.logging.models.RequestMetadata;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import com.goodsoft.infra.modulecore.logging.models.LoggingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class MicroserviceApplicationConfiguration
{
    private @Autowired HttpServletRequest request;

    @Bean
    @Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestMetadata requestMetadata(HttpServletRequest request) {

        var correlationId = request.getHeader("GS-CorrelationId");

        var configuration = new RequestMetadata();
        configuration.setCorrelationId(correlationId);

        return configuration;
    }
}