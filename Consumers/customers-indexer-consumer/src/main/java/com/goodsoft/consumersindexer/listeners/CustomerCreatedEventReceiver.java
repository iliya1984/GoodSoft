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
public class CustomerCreatedEventReceiver
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
}
