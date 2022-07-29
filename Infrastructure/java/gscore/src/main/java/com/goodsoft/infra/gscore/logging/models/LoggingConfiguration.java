package com.goodsoft.infra.gscore.logging.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goodsoft.infra.gscore.configuration.MessageBrokerConfiguration;
import lombok.Getter;
import lombok.Setter;

public class LoggingConfiguration
{
    @Getter
    @Setter
    private String serviceName;

    @JsonProperty("messageBroker")
    @Getter
    @Setter
    private MessageBrokerConfiguration messageBroker;

    @Getter
    @Setter
    private String loggingDomainName;

    @Getter
    @Setter
    private String loggingModuleName;
}
