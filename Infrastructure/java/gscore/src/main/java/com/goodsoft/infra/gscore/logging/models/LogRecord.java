package com.goodsoft.infra.gscore.logging.models;

import com.goodsoft.infra.gscore.logging.enums.LogLevel;
import lombok.Getter;
import lombok.Setter;

public class LogRecord
{
    @Getter
    @Setter
    private LogRecordMetadata metadata;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private LogLevel level;

    @Getter
    @Setter
    private String stackTrace;
}
