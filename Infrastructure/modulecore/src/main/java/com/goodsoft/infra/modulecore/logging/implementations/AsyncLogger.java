package com.goodsoft.infra.modulecore.logging.implementations;

import com.goodsoft.infra.modulecore.logging.models.RequestMetadata;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import com.goodsoft.infra.modulecore.logging.abstractions.ILoggerMessageProducer;
import com.goodsoft.infra.modulecore.logging.enums.LogLevel;
import com.goodsoft.infra.modulecore.logging.models.LogRecord;
import com.goodsoft.infra.modulecore.logging.models.LogRecordMetadata;
import com.goodsoft.infra.modulecore.logging.models.LoggingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class AsyncLogger implements ILogger
{
    private ILoggerMessageProducer _producer;
    private LoggingConfiguration _configuration;

    @Autowired
    private ApplicationContext context;

    public AsyncLogger(
            ILoggerMessageProducer producer,
            IConfigurationManager<LoggingConfiguration> loggingConfigurationManager)
    {
        _producer = producer;
        _configuration = loggingConfigurationManager.getConfiguration();
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
        var requestMetadata = (RequestMetadata)context.getBean("requestMetadata");

        var metadata = new LogRecordMetadata();
        metadata.setDomainName(_configuration.getLoggingDomainName());
        metadata.setModuleName(_configuration.getLoggingModuleName());
        metadata.setCorrelationId(requestMetadata.getCorrelationId());

        record.setMetadata(metadata);

        _producer.notifyRecordCreated(record);
    }
}
