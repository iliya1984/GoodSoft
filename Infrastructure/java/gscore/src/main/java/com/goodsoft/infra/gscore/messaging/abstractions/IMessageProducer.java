package com.goodsoft.infra.gscore.messaging.abstractions;

import com.goodsoft.infra.gscore.messaging.entities.Message;
import com.goodsoft.infra.gscore.messaging.entities.MessageSendResult;

public interface IMessageProducer
{
    <T> MessageSendResult send(Message message);
}
