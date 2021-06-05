package com.goodsoft.configurationservice.logic;

import com.goodsoft.configurationservice.entities.ConfigurationSection;

import java.util.HashMap;
import java.util.Objects;

public interface IConfigurationServiceManager
{
    ConfigurationSection getMicroserviceConfiguration(String serviceName);
}
