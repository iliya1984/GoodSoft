package com.goodsoft.customersservice.dal.abstractions.customers;

import com.goodsoft.customersservice.entities.CustomerEntity;

public interface ICustomersDal
{
    CustomerEntity create(CustomerEntity customer);
}
