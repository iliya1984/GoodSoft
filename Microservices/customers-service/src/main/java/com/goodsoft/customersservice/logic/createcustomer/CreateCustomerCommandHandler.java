package com.goodsoft.customersservice.logic.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.entities.Customer;

@RequestHanlder()
public class CreateCustomerCommandHandler implements Command.Handler<CreateCustomerCommand, Customer>
{
    @Override
    public Customer handle(CreateCustomerCommand createCustomerRequest) {
        return null;
    }
}
