package com.goodsoft.infra.modulecore.logging.implementations;

import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import com.goodsoft.infra.modulecore.logging.enums.LogLevel;
import com.goodsoft.infra.modulecore.logging.models.LogRecord;
import lombok.extern.java.Log;

public class AsyncLogger implements ILogger
{
    public void LogError(String error)
    {
        var record = new LogRecord();
        record.setLevel(LogLevel.Error);
        record.setMessage(error);

        LogRecord(record);
    }

    public void LogError(String error, String errorCode)
    {
        var record = new LogRecord();
        record.setLevel(LogLevel.Error);
        record.setMessage(error);
        record.setCode(errorCode);

        LogRecord(record);
    }

    public void LogError(Exception ex)
    {
        var record = new LogRecord();
        record.setLevel(LogLevel.Error);
        record.setMessage(ex.getMessage());

        var stackTrace = ex.getStackTrace().toString();

        record.setStackTrace(stackTrace);

        LogRecord(record);

    }

    private void LogRecord(LogRecord record)
    {

    }
}
