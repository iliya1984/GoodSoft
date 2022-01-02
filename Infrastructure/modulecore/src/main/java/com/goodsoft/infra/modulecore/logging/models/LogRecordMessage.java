package com.goodsoft.infra.modulecore.logging.models;

import com.goodsoft.infra.modulecore.logging.enums.LogLevel;
import lombok.Getter;
import lombok.Setter;

public class LogRecordMessage
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
