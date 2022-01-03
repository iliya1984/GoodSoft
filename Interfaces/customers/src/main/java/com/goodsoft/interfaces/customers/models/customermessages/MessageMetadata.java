package com.goodsoft.interfaces.customers.models.customermessages;

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
}
