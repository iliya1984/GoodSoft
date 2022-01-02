package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goodsoft.infra.modulecore.configuration.DatasourceConfiguration;
import com.goodsoft.infra.modulecore.configuration.MessageBrokerConfiguration;
import com.goodsoft.infra.modulecore.configuration.MessageRoutingItem;
import com.goodsoft.infra.modulecore.configuration.SearchServerConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class CustomerServiceConfiguration
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

    public MessageRoutingItem findMessageRoutingByName(String name)
    {
        var messageBrokerConfiguration  = getMessageBroker();
        if(messageBrokerConfiguration == null)
        {
            return null;
        }

        var routing = messageBrokerConfiguration.getRoutingItems();
        if(routing == null || routing.isEmpty())
        {
            return null;
        }

        var routingItem = routing.stream()
                .filter(ri -> ri.getAction() != null && ri.getAction().toLowerCase(Locale.ROOT).equals(name))
                .findFirst();

        return routingItem.get();
    }
}
