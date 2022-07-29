package com.goodsoft.infra.modulecore.logging.abstractions;

import java.util.UUID;

public interface ILogger
{

    UUID getLoggerId();

    void setCorrelationId(String correlationId);

    void logError(String error);
    void logError(String error, String errorCode);
    void logError(Exception ex);
}
