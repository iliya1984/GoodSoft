package com.goodsoft.infra.gscore.logging.models;

import lombok.Getter;
import lombok.Setter;

public class RequestMetadata
{
    @Getter
    @Setter
    private String correlationId;
}
