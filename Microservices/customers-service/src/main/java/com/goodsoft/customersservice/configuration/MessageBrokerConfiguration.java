package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MessageBrokerConfiguration
{
    @Getter
    @Setter
    @JsonProperty("type")
    private String type;

    @Getter
    @Setter
    @JsonProperty("routing")
    private List<MessageRoutingItem> routingItems;

}
