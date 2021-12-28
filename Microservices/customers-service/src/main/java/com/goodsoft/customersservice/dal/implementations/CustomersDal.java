package com.goodsoft.customersservice.dal.implementations;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.ICustomersDal;
import com.goodsoft.customersservice.entities.CustomerEntity;
import org.springframework.stereotype.Service;
import org.bson.Document;

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

        collection.insertOne(document);

        return customer;
    }
}
