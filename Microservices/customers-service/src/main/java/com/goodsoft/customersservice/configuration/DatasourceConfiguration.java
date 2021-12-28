package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class DatasourceConfiguration
{
    @Getter
    @Setter
    @JsonProperty("type")
    private String datasourceType;

    @Getter
    @Setter
    @JsonProperty("connectionString")
    private String connectionString;

    @Getter
    @Setter
    @JsonProperty("user")
    private String user;
}
