package com.goodsoft.infra.modulecore.logging.abstractions;

public interface ILogger
{
    void logError(String error);
    void logError(String error, String errorCode);
    void logError(Exception ex);
}
