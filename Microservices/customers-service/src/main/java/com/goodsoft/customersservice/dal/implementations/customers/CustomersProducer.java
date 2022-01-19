package com.goodsoft.customersservice.dal.implementations.customers;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersProducer;
import com.goodsoft.customersservice.entities.customers.CustomerEntity;
import com.goodsoft.infra.modulecore.logging.models.RequestMetadata;
import com.goodsoft.infra.modulecore.messaging.abstractions.IMessageProducer;
import com.goodsoft.infra.modulecore.messaging.entities.Message;
import com.goodsoft.infra.modulecore.messaging.entities.MessageMetadata;
import com.goodsoft.interfaces.customers.models.customermessages.CustomerCreatedMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomersProducer implements ICustomersProducer
{
    private final String Action = "customercreate";

    private IMessageProducer _messageProducer;
    private CustomerServiceConfiguration _configuration;
    private RequestMetadata _requestMetadata;

    public CustomersProducer(
            CustomerServiceConfiguration configuration,
            IMessageProducer messageProducer,
            RequestMetadata requestMetadata)
    {
        _messageProducer = messageProducer;
        _configuration = configuration;
        _requestMetadata = requestMetadata;
    }

    public void notifyCustomerCreated(CustomerEntity customer)
    {
        var messageData = new CustomerCreatedMessage();
        messageData.setFirstName(customer.getFirstName());
        messageData.setLastName(customer.getLastName());
        messageData.setId(customer.getId());

        if(customer.getEmails() != null)
        {
            var primaryEmail = customer.getEmails().stream().findFirst();
            if(primaryEmail != null && false == primaryEmail.isEmpty())
            {
                messageData.setEmail(primaryEmail.get().getEmail());
            }
        }

        var metadata = new MessageMetadata();
        metadata.setCorrelationId(_requestMetadata.getCorrelationId());
        metadata.setAction(Action);

        var message = new Message<CustomerCreatedMessage>();
        message.setMetadata(metadata);
        message.setData(messageData);

        var result = _messageProducer.send(message);
    }
}
