package com.goodsoft.consumersindexer.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goodsoft.infra.modulecore.configuration.DatasourceConfiguration;
import com.goodsoft.infra.modulecore.configuration.MessageBrokerConfiguration;
import com.goodsoft.infra.modulecore.configuration.SearchServerConfiguration;
import lombok.Getter;
import lombok.Setter;

public class CustomersConsumerConfiguration
{
    @Getter
    @Setter
    private String serviceName;

    @JsonProperty("dataSource")
    @Getter
    @Setter
    private DatasourceConfiguration datasource;

    @JsonProperty("searchServer")
    @Getter
    @Setter
    private SearchServerConfiguration searchServer;

    @JsonProperty("messageBroker")
    @Getter
    @Setter
    private MessageBrokerConfiguration messageBroker;
}
