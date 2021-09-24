package com.goodsoft.consumersindexer.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodsoft.consumersindexer.models.CustomerEntry;
import com.goodsoft.interfaces.customers.entities.Customer;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Component
public class CustomerCreatedEventReceiver //implements MessageListener
{
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Customer message)
    {



        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    /*public void onMessage(Message message)
    {
        var messageJson = message.toString();
        var messageBody = message.getBody();
        var messageBodyJson =  DatatypeConverter.printBase64Binary(messageBody);

        var mapper = new ObjectMapper();
        try
        {
            var customer = mapper.readValue(messageBody, CustomerEntry.class);

            var i = 1;
        }
        catch (IOException e)
        {
            //TODO: Log exception
        }

        System.out.println("Consuming Message - " + new String(message.getBody()));
    }*/

}
