package com.goodsoft.customersservice.logic.getcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.configuration.IConfigurationManager;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.models.customers.CustomerEmailModel;
import com.goodsoft.interfaces.customers.models.customers.CustomerModel;

import java.util.ArrayList;

@RequestHanlder()
public class GetCustomerQueryHandler implements Command.Handler<GetCustomerQuery, CustomerModel>
{
    private CustomerServiceConfiguration _config;

    public GetCustomerQueryHandler(CustomerServiceConfiguration configuration)
    {
        _config = configuration;
    }
    @Override
    public CustomerModel handle(GetCustomerQuery request)
    {
        var customer = new CustomerModel();
        customer.setFirstName("Iliya");
        customer.setLastName("Nahshan");

        var primaryEmail = new CustomerEmailModel();
        primaryEmail.setEmail("iliya.nahshan@gmail.com");
        primaryEmail.setPrimary(true);

        var emails = new ArrayList<CustomerEmailModel>();
        emails.add(primaryEmail);
        customer.setEmail(emails);

        return customer;
    }
}
