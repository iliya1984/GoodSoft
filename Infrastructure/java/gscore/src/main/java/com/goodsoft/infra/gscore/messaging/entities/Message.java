package com.goodsoft.infra.gscore.messaging.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;

public class Message
{
    @Getter
    @Setter
    private MessageMetadata metadata;

    @Getter
    @Setter
    private String messageId;

    @Getter
    @Setter
    private Object data;

    public Message()
    {
        setMetadata(new MessageMetadata());
    }
}
