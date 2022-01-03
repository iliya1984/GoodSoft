package com.goodsoft.infra.consumer.abstractions;

import com.goodsoft.infra.consumer.models.Message;

public interface IConsumer<T>
{
    void receiveMessage(Message<T> message);
}
