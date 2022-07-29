package com.goodsoft.infra.modulecore.logging.implementations;

import com.goodsoft.infra.modulecore.logging.models.*;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import com.goodsoft.infra.modulecore.logging.abstractions.ILoggerMessageProducer;
import com.goodsoft.infra.modulecore.logging.enums.LogLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.UUID;

public class AsyncLogger implements ILogger
{
    private ILoggerMessageProducer _producer;
    private LoggingConfiguration _configuration;
    private UUID _loggerId;
    private LoggingOptions _options;

    @Autowired
    private ApplicationContext context;

    public AsyncLogger(
            ILoggerMessageProducer producer,
            IConfigurationManager<LoggingConfiguration> loggingConfigurationManager)
    {
        _loggerId = UUID.randomUUID();
        _producer = producer;
        _configuration = loggingConfigurationManager.getConfiguration();

        _options = new LoggingOptions();
    }

    public UUID getLoggerId()
    {
        return _loggerId;
    }

    public void setCorrelationId(String correlationId)
    {
        _options.setCorrelationId(correlationId);
    }

    public void logError(String error)
    {
        var record = new LogRecord();
        record.setLevel(LogLevel.Error);
        record.setMessage(error);

        logRecord(record);
    }

    public void logError(String error, String errorCode)
    {
        var record = new LogRecord();
        record.setLevel(LogLevel.Error);
        record.setMessage(error);
        record.setCode(errorCode);

        logRecord(record);
    }

    public void logError(Exception ex)
    {
        var record = new LogRecord();
        record.setLevel(LogLevel.Error);
        record.setMessage(ex.getMessage());

        var stackTrace = ex.getStackTrace().toString();

        record.setStackTrace(stackTrace);

        logRecord(record);

    }

    private void logRecord(LogRecord record)
    {
        String correlationId = null;
        if(_options == null || _options.getCorrelationId() == null)
        {
            try
            {
                var requestMetadata = (RequestMetadata)context.getBean("requestMetadata");
                correlationId = requestMetadata.getCorrelationId();
            }
            catch (Exception ex)
            {

            }
        }
        else
        {
            correlationId = _options.getCorrelationId();
        }

        var metadata = new LogRecordMetadata();
        metadata.setDomainName(_configuration.getLoggingDomainName());
        metadata.setModuleName(_configuration.getLoggingModuleName());
        metadata.setCorrelationId(correlationId);

        record.setMetadata(metadata);

        _producer.notifyRecordCreated(record);
    }
}
