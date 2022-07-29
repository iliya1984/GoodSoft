package com.goodsoft.infra.gscore.configuration;

import org.springframework.stereotype.Service;

public interface IConfigurationManager<T>
{
    T getConfiguration() ;
}
