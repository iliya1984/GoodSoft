package com.goodsoft.interfaces.customers.requestresponse;

import com.goodsoft.interfaces.customers.entities.Customer;
import lombok.Getter;
import lombok.Setter;
public class GetCustomerResponse extends CustomersServiceResponse
{
    @Getter
    @Setter
    private Customer customer;
}
