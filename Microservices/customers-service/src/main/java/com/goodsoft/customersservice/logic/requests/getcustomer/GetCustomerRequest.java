package com.goodsoft.customersservice.logic.requests.getcustomer;


import an.awesome.pipelinr.Command;
import com.goodsoft.interfaces.customers.entities.Customer;
import lombok.Getter;
import lombok.Setter;

public class GetCustomerRequest implements Command<Customer>
{
    @Getter
    @Setter
    private Long customerId;
}
