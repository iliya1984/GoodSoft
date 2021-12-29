package com.goodsoft.customersservice.logic.getcustomer;


import an.awesome.pipelinr.Command;
import com.goodsoft.interfaces.customers.models.customers.CustomerModel;
import lombok.Getter;
import lombok.Setter;

public class GetCustomerQuery implements Command<CustomerModel>
{
    @Getter
    @Setter
    private Long customerId;
}
