package com.goodsoft.loggingconsumer.models;

import com.goodsoft.infra.modulecore.logging.enums.LogLevel;
import com.goodsoft.infra.modulecore.logging.models.LogRecordMetadata;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class LogRecordEntry
{
    @Getter
    @Setter
    private String correlationId;

    @Getter
    @Setter
    private String dateCreated;

    @Getter
    @Setter
    private String domainName;

    @Getter
    @Setter
    private String moduleName;

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
