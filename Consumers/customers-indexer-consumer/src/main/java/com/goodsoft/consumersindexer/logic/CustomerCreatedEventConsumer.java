package com.goodsoft.consumersindexer.logic;

import com.goodsoft.consumersindexer.dal.abstractions.ICustomersRepository;
import com.goodsoft.consumersindexer.models.CustmerCreatedMessageWrapper;
import com.goodsoft.consumersindexer.models.CustomerEntry;
import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import com.goodsoft.infra.modulecore.logging.abstractions.ILoggerFactory;
import com.goodsoft.infra.modulecore.messaging.entities.Message;
import com.goodsoft.interfaces.customers.models.customermessages.CustomerCreatedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    public void receiveMessage(Message<CustomerCreatedMessage> message)
    {
        try
        {
            var customerMessage = message.getData();

            var customer = new CustomerEntry();
            customer.setFirstName(customerMessage.getFirstName());
            customer.setLastName(customerMessage.getLastName());

            var id = customerMessage.getId();
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

    private ILogger createLogger(Message<CustomerCreatedMessage> message)
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
