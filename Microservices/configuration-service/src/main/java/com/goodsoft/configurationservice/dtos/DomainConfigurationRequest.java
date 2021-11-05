package com.goodsoft.configurationservice.dtos;

import lombok.Getter;
import lombok.Setter;

public class DomainConfigurationRequest
{
    @Getter
    @Setter
    private String domainName;

    @Getter
    @Setter
    private String key;
}
