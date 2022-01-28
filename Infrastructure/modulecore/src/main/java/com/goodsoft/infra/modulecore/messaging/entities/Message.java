package com.goodsoft.infra.modulecore.messaging.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("data")
    private T data;

    public Message()
    {
        setMetadata(new MessageMetadata());
    }
}
