package com.goodsoft.interfaces.customers.models;

import com.goodsoft.interfaces.customers.entities.Customer;
import lombok.Getter;
import lombok.Setter;

public class GetCustomerResponse
{
    @Getter
    @Setter
    private Customer customer;
}
