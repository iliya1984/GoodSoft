package com.goodsoft.infra.modulecore.logging.models;

import lombok.Getter;
import lombok.Setter;

public class RequestMetadata
{
    @Getter
    @Setter
    private String correlationId;
}
