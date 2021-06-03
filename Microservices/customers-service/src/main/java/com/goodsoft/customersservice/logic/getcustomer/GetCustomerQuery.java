package com.goodsoft.customersservice.logic.getcustomer;


import an.awesome.pipelinr.Command;
import com.goodsoft.interfaces.customers.entities.Customer;
import lombok.Getter;
import lombok.Setter;

public class GetCustomerQuery implements Command<Customer>
{
    @Getter
    @Setter
    private Long customerId;
}
