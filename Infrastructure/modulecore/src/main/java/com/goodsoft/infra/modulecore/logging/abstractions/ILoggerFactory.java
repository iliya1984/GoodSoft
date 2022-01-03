package com.goodsoft.infra.modulecore.logging.abstractions;

public interface ILoggerFactory
{
    ILogger create(String correlationId);
}
