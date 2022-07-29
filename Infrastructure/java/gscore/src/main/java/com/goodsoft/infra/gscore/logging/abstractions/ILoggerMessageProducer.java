package com.goodsoft.infra.gscore.logging.abstractions;

import com.goodsoft.infra.gscore.logging.models.LogRecord;

public interface ILoggerMessageProducer
{
    void notifyRecordCreated(LogRecord record);
}
