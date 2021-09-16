package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goodsoft.interfaces.customers.models.CreateCustomerModel;
import lombok.Getter;
import lombok.Setter;

public class ConfigurationSection
{
    @Getter
    @Setter
    private String serviceName;

    @JsonProperty("datasource")
    @Getter
    @Setter
    private DatasourceConfiguration datasource;
}
