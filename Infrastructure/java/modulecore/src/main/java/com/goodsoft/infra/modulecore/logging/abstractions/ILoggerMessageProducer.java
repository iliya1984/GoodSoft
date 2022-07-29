package com.goodsoft.infra.modulecore.logging.abstractions;

import com.goodsoft.infra.modulecore.logging.models.LogRecord;

public interface ILoggerMessageProducer
{
    void notifyRecordCreated(LogRecord record);
}
