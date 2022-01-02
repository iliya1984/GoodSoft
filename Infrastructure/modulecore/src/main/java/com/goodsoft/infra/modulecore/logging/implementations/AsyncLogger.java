package com.goodsoft.infra.modulecore.logging.implementations;

import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import com.goodsoft.infra.modulecore.logging.abstractions.ILoggerMessageProducer;
import com.goodsoft.infra.modulecore.logging.enums.LogLevel;
import com.goodsoft.infra.modulecore.logging.models.LogRecord;
import com.goodsoft.infra.modulecore.logging.models.LogRecordMetadata;
import com.goodsoft.infra.modulecore.logging.models.LoggingConfiguration;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AsyncLogger implements ILogger
{
    private ILoggerMessageProducer _producer;
    private LoggingConfiguration _configuration;

    public AsyncLogger(ILoggerMessageProducer producer, LoggingConfiguration configuration)
    {
        _producer = producer;
        _configuration = configuration;
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
        var metadata = new LogRecordMetadata();
        metadata.setDomainName(_configuration.getLoggingDomainName());
        metadata.setModuleName(_configuration.getLoggingModuleName());

        record.setMetadata(metadata);

        _producer.notifyRecordCreated(record);
    }
}
