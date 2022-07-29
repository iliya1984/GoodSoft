package com.goodsoft.infra.gscore.logging.models;

import lombok.Getter;
import lombok.Setter;

public class LogRecordMetadata
{
    @Getter
    @Setter
    private String domainName;

    @Getter
    @Setter
    private String moduleName;

    @Getter
    @Setter
    private String correlationId;
}
