package com.goodsoft.configurationservice.logic;

import com.goodsoft.configurationservice.dtos.DomainConfiguration;
import com.goodsoft.configurationservice.dtos.DomainConfigurationRequest;

public interface IConfigurationServiceManager
{
    DomainConfiguration getDomainConfiguration(DomainConfigurationRequest request);
}
