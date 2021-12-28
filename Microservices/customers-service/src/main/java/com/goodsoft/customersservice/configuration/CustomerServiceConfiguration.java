package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class CustomerServiceConfiguration
{
    @Getter
    @Setter
    private String serviceName;

    @JsonProperty("dataSource")
    @Getter
    @Setter
    private DatasourceConfiguration datasource;

    @JsonProperty("messageBroker")
    @Getter
    @Setter
    private MessageBrokerConfiguration messageBroker;
}
