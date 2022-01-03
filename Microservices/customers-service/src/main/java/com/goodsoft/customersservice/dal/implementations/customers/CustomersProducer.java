package com.goodsoft.customersservice.dal.implementations.customers;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersProducer;
import com.goodsoft.customersservice.entities.customers.CustomerEntity;
import com.goodsoft.infra.modulecore.logging.models.RequestMetadata;
import com.goodsoft.interfaces.customers.models.customermessages.CustomerCreatedMessage;
import com.goodsoft.interfaces.customers.models.customermessages.MessageMetadata;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomersProducer implements ICustomersProducer
{
    private final String Action = "customercreate";

    private AmqpTemplate _rabbitTemplate;
    private CustomerServiceConfiguration _configuration;
    private RequestMetadata _requestMetadata;

    public CustomersProducer(
            CustomerServiceConfiguration configuration,
            AmqpTemplate rabbitMQTemplate,
            RequestMetadata requestMetadata)
    {
        _rabbitTemplate = rabbitMQTemplate;
        _configuration = configuration;
        _requestMetadata = requestMetadata;
    }

    public void notifyCustomerCreated(CustomerEntity customer)
    {
        var message = new CustomerCreatedMessage();
        message.setFirstName(customer.getFirstName());
        message.setLastName(customer.getLastName());
        message.setId(customer.getId());

        if(customer.getEmails() != null)
        {
            var primaryEmail = customer.getEmails().stream().findFirst();
            if(primaryEmail != null && false == primaryEmail.isEmpty())
            {
                message.setEmail(primaryEmail.get().getEmail());
            }
        }

        var metadata = new MessageMetadata();
        metadata.setCorrelationId(_requestMetadata.getCorrelationId());

        message.setMetadata(metadata);

        var messageRouting = _configuration.findMessageRoutingByName(Action);
        if(messageRouting != null)
        {
            var exchange = messageRouting.getExchange();
            var routingKey = messageRouting.getRoutingKey();
            _rabbitTemplate.convertAndSend(exchange, routingKey, message);
        }
    }
}
