package com.goodsoft.infra.consumer.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Message<T>
{
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private MessageMetadata metadata;

    @Getter
    @Setter
    private T content;

}
