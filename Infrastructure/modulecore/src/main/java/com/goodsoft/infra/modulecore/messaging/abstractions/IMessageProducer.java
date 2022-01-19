package com.goodsoft.infra.modulecore.messaging.abstractions;

import com.goodsoft.infra.modulecore.messaging.entities.Message;
import com.goodsoft.infra.modulecore.messaging.entities.MessageSendResult;

public interface IMessageProducer
{
    <T> MessageSendResult send(Message<T> message);
}
