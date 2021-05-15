package com.goodsoft.customersservice.logic.requests.getcustomer;


import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.entities.Customer;
import lombok.Getter;
import lombok.Setter;

public class GetCustomerRequest implements Command<Customer>
{
    @Getter
    @Setter
    private Long customerId;
}
