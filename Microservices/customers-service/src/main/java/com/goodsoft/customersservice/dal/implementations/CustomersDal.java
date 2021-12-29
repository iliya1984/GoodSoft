package com.goodsoft.customersservice.dal.implementations;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.ICustomersDal;
import com.goodsoft.customersservice.entities.CustomerEmailEntity;
import com.goodsoft.customersservice.entities.CustomerEntity;
import com.goodsoft.interfaces.customers.models.customermessages.CustomerCreatedMessage;
import com.mongodb.DBObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.bson.Document;

import java.util.ArrayList;

@Service
public class CustomersDal extends BaseDal implements ICustomersDal
{
    private final String Action = "customercreate";

    private AmqpTemplate _rabbitTemplate;

    public CustomersDal(CustomerServiceConfiguration configuration, AmqpTemplate rabbitMQTemplate)
    {
        super(configuration);

        _rabbitTemplate = rabbitMQTemplate;
    }

    @Override
    public CustomerEntity create(CustomerEntity customer) {

        var customerId = java.util.UUID.randomUUID();
        customer.setId(customerId);

        insertCustomer(customer);
        notifyCustomerCreated(customer);

        return customer;
    }

    private  void notifyCustomerCreated(CustomerEntity customer)
    {
        var message = new CustomerCreatedMessage();
        message.setFirstName(customer.getFirstName());
        message.setLastName(customer.getLastName());

        if(customer.getEmails() != null)
        {
            var primaryEmail = customer.getEmails().stream().findFirst();
            if(primaryEmail != null && false == primaryEmail.isEmpty())
            {
                message.setEmail(primaryEmail.get().getEmail());
            }
        }

        var messageRouting = _configuration.findMessageRoutingByName(Action);
        if(messageRouting != null)
        {
            var exchange = messageRouting.getExchange();
            var routingKey = messageRouting.getRoutingKey();
            _rabbitTemplate.convertAndSend(exchange, routingKey, message);
        }
    }

    private void insertCustomer(CustomerEntity customer)
    {
        var collection = _database.getCollection("customers");

        Document document = new Document();
        document.append("customerId", customer.getId().toString());
        document.append("firstName", customer.getFirstName());
        document.append("lastName", customer.getLastName());

        var emails = new ArrayList<Document>();

        for (CustomerEmailEntity email : customer.getEmails())
        {
            var emailDocument = new Document();
            emailDocument.append("email", email.getEmail());
            emailDocument.append("isPrimary", email.isPrimary());
            emails.add(emailDocument);
        }
        document.append("emails", emails);

        collection.insertOne(document);
    }
}
