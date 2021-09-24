package com.goodsoft.consumersindexer.listeners;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerCreatedEventListener implements MessageListener {

    public void onMessage(Message message)
    {
        var messageJson = message.toString();
        var messageBodyJson = message.getBody().toString();

        System.out.println("Consuming Message - " + new String(message.getBody()));
    }

}
