package com.goodsoft.configurationservice.logic;

import com.goodsoft.configurationservice.entities.DomainConfiguration;

public interface IConfigurationServiceManager
{
    DomainConfiguration getMicroserviceConfiguration(String serviceName);
}
