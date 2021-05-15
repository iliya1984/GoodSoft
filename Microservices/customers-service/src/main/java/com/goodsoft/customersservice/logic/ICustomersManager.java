package com.goodsoft.customersservice.logic;

import com.goodsoft.customersservice.entities.Customer;

import java.util.List;

public interface ICustomersManager
{
    List<Customer> GetById(Long customerId);
}
