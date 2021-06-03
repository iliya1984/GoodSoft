package com.goodsoft.customersservice.logic.requests.getcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.entities.Customer;
import com.goodsoft.interfaces.customers.entities.CustomerEmail;

import java.util.ArrayList;

@RequestHanlder()
public class GetCustomerRequestHandler implements Command.Handler<GetCustomerRequest, Customer>
{
    @Override
    public Customer handle(GetCustomerRequest request)
    {
        var customer = new Customer();
        customer.setFirstName("Iliya");
        customer.setLastName("Nahshan");

        var primaryEmail = new CustomerEmail();
        primaryEmail.setEmail("iliya.nahshan@gmail.com");
        primaryEmail.setPrimary(true);

        var emails = new ArrayList<CustomerEmail>();
        emails.add(primaryEmail);
        customer.setEmails(emails);

        return customer;
    }
}
