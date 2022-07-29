package com.goodsoft.infra.consumer.models;

import lombok.Getter;
import lombok.Setter;

public class MessageMetadata
{
    @Getter
    @Setter
    private String correlationId;

    @Getter
    @Setter
    private String requestedBy;

    @Getter
    @Setter
    private String requestedByDomain;
}
