package com.goodsoft.customersservice.dal.abstractions.customers;

import com.goodsoft.customersservice.entities.customers.CustomerEntity;

public interface ICustomersProducer
{
    void notifyCustomerCreated(CustomerEntity customer);
}
