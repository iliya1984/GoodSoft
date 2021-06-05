package com.goodsoft.configurationservice.logic;

import java.util.HashMap;
import java.util.Objects;

public interface IConfigurationServiceManager
{
    Objects getMicroserviceConfiguration(String serviceName);
}
