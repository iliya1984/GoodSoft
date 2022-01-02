package com.goodsoft.consumersindexer.logic;

import com.goodsoft.consumersindexer.dal.abstractions.ICustomersRepository;
import com.goodsoft.consumersindexer.models.CustomerEntry;
import com.goodsoft.interfaces.customers.models.customermessages.CustomerCreatedMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class CustomerCreatedEventConsumer
{
    private ICustomersRepository _repository;

    public CustomerCreatedEventConsumer(ICustomersRepository repository)
    {
        _repository = repository;
    }

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(CustomerCreatedMessage message)
    {
        var customer = new CustomerEntry();
        customer.setFirstName(message.getFirstName());
        customer.setLastName(message.getLastName());

        var id = message.getId();
        if(id != null)
        {
            customer.setCustomerId(id.toString());
        }

        _repository.save(customer);

        //var customers = _repository.findAll();

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
