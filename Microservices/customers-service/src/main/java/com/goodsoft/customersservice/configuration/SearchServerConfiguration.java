package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class SearchServerConfiguration
{
    @Getter
    @Setter
    @JsonProperty("host")
    private String host;

    @Getter
    @Setter
    @JsonProperty("port")
    private int port;
}
