package com.goodsoft.infra.gscore.messaging.entities;

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
    private String action;
}
