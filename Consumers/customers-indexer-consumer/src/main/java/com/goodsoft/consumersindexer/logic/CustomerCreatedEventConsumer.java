package com.goodsoft.consumersindexer.logic;

import com.goodsoft.consumersindexer.dal.abstractions.ICustomersRepository;
import com.goodsoft.consumersindexer.models.CustomerEntry;
import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import com.goodsoft.infra.modulecore.logging.abstractions.ILoggerFactory;
import com.goodsoft.interfaces.customers.models.customermessages.CustomerCreatedMessage;
import com.goodsoft.interfaces.customers.models.customermessages.MessageBase;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class CustomerCreatedEventConsumer
{
    private ICustomersRepository _repository;
    private ILoggerFactory _loggerFactory;

    public CustomerCreatedEventConsumer(ICustomersRepository repository, ILoggerFactory loggerFactory)
    {
        _repository = repository;
        _loggerFactory = loggerFactory;
    }

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(CustomerCreatedMessage message)
    {
        try
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
        }
        catch (Exception ex)
        {
            createLogger(message).logError(ex);
        }
        finally
        {
            latch.countDown();
        }
    }

    private ILogger createLogger(MessageBase message)
    {
        String correlationId = null;
        if(message.getMetadata() != null)
        {
            correlationId = message.getMetadata().getCorrelationId();
        }

        var logger = _loggerFactory.create(correlationId);
        return logger;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
