package com.goodsoft.configurationservice.dtos;

import lombok.Getter;
import lombok.Setter;

public class DomainConfiguration
{
    @Getter
    @Setter
    private String domainName;

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private String value;
}
