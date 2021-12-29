package com.goodsoft.customersservice.logic.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersDal;
import com.goodsoft.customersservice.entities.customers.CustomerEmailEntity;
import com.goodsoft.customersservice.entities.customers.CustomerEntity;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;

import java.util.UUID;

@RequestHanlder()
public class CreateCustomerCommandHandler implements Command.Handler<CreateCustomerCommand, UUID>
{
    private CustomerServiceConfiguration _configuration;
    private ICustomersDal _dal;

    public CreateCustomerCommandHandler
            (
                    ICustomersDal dal,
                    CustomerServiceConfiguration configuration
            )
    {
        _configuration = configuration;
        _dal = dal;
    }

    @Override
    public UUID handle(CreateCustomerCommand createCustomerRequest)
    {
        var model = createCustomerRequest.getModel();

        var customer = new CustomerEntity();
        customer.setFirstName(model.getFirstName());
        customer.setLastName(model.getLastName());

        var primaryEmail = new CustomerEmailEntity();
        primaryEmail.setEmail(model.getEmail());
        primaryEmail.setPrimary(true);

        customer.getEmails().add(primaryEmail);

        var result = _dal.create(customer);

        return result.getId();
    }


}
