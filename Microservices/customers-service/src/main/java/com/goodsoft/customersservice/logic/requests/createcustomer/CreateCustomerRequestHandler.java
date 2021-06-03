package com.goodsoft.customersservice.logic.requests.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.entities.Customer;
import com.goodsoft.interfaces.customers.models.CreateCustomerResponse;

@RequestHanlder()
public class CreateCustomerRequestHandler implements Command.Handler<CreateCustomerCommand, Customer>
{
    @Override
    public Customer handle(CreateCustomerCommand createCustomerRequest) {
        return null;
    }
}
