package com.goodsoft.infra.modulecore.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MessageRoutingItem
{
    @Getter
    @Setter
    @JsonProperty("action")
    private String action;

    @Getter
    @Setter
    @JsonProperty("exchange")
    private String exchange;

    @Getter
    @Setter
    @JsonProperty("routingKey")
    private String routingKey;

    @Getter
    @Setter
    @JsonProperty("queue")
    private String queue;
}
