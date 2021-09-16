package com.goodsoft.customersservice.logic.getcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.configuration.ConfigurationSection;
import com.goodsoft.customersservice.configuration.IConfigurationManager;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.entities.Customer;
import com.goodsoft.interfaces.customers.entities.CustomerEmail;

import java.util.ArrayList;

@RequestHanlder()
public class GetCustomerQueryHandler implements Command.Handler<GetCustomerQuery, Customer>
{
    private ConfigurationSection _config;

    public GetCustomerQueryHandler(IConfigurationManager configurationManager)
    {
        _config = configurationManager.GetConfiguration();
    }
    @Override
    public Customer handle(GetCustomerQuery request)
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
