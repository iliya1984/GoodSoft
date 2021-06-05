package com.goodsoft.customersservice.logic.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.interfaces.customers.entities.Customer;
import com.goodsoft.interfaces.customers.models.CreateCustomerModel;
import lombok.Getter;
import lombok.Setter;

public class CreateCustomerCommand implements Command<Customer>
{
    @Getter
    @Setter
    private CreateCustomerModel model;
}
