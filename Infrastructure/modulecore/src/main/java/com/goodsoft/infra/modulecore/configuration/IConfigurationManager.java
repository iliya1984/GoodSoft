package com.goodsoft.infra.modulecore.configuration;

import org.springframework.stereotype.Service;

public interface IConfigurationManager<T>
{
    T getConfiguration() ;
}
