package com.goodsoft.infra.modulecore.messaging.entities;

import lombok.Getter;
import lombok.Setter;

public class Message<T>
{
    @Getter
    @Setter
    private MessageMetadata metadata;

    @Getter
    @Setter
    private String messageId;

    @Getter
    @Setter
    private T data;

    public Message()
    {
        setMetadata(new MessageMetadata());
    }
}
