package com.goodsoft.customersservice.dal.implementations;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.ICustomersDal;
import com.goodsoft.customersservice.entities.CustomerEmailEntity;
import com.goodsoft.customersservice.entities.CustomerEntity;
import com.mongodb.DBObject;
import org.springframework.stereotype.Service;
import org.bson.Document;

import java.util.ArrayList;

@Service
public class CustomersDal extends BaseDal implements ICustomersDal
{
    public CustomersDal(CustomerServiceConfiguration configuration)
    {
        super(configuration);
    }

    @Override
    public CustomerEntity Create(CustomerEntity customer) {

        var customerId = java.util.UUID.randomUUID();

        customer.setId(customerId);

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

        return customer;
    }
}
