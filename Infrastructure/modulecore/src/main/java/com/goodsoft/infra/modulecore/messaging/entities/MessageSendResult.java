package com.goodsoft.infra.modulecore.messaging.entities;

import com.goodsoft.infra.modulecore.messaging.enums.MessageSendStatusCode;
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
