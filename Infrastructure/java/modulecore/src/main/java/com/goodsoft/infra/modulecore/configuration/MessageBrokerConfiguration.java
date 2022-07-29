package com.goodsoft.infra.modulecore.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Locale;

public class MessageBrokerConfiguration
{
    @Getter
    @Setter
    @JsonProperty("connectionString")
    private String connectionString;

    @Getter
    @Setter
    @JsonProperty("type")
    private String type;

    @Getter
    @Setter
    @JsonProperty("routing")
    private List<MessageRoutingItem> routingItems;

    public MessageRoutingItem findMessageRoutingByName(String name)
    {
        var routing = getRoutingItems();
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
