package com.goodsoft.infra.gscore.messaging.entities;

import com.goodsoft.infra.gscore.messaging.enums.MessageSendStatusCode;
import lombok.Getter;
import lombok.Setter;

public class MessageSendResult
{
    @Getter
    @Setter
    private String messageId;

    @Getter
    @Setter
    private MessageSendStatusCode status;
}
