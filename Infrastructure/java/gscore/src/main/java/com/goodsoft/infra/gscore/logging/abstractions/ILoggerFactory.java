package com.goodsoft.infra.gscore.logging.abstractions;

public interface ILoggerFactory
{
    ILogger create(String correlationId);
}
