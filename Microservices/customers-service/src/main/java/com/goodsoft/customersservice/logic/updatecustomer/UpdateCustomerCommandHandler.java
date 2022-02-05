package com.goodsoft.customersservice.logic.updatecustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersDal;
import com.goodsoft.customersservice.entities.customers.CustomerEmailEntity;
import com.goodsoft.customersservice.entities.customers.CustomerEntity;
import com.goodsoft.customersservice.logic.createcustomer.CreateCustomerCommand;
import com.goodsoft.interfaces.customers.models.customers.UpdateCustomerModel;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class UpdateCustomerCommandHandler  implements Command.Handler<UpdateCustomerCommand, UUID>
{
    private CustomerServiceConfiguration _configuration;
    private ICustomersDal _dal;

    public UpdateCustomerCommandHandler
            (
                    ICustomersDal dal,
                    CustomerServiceConfiguration configuration
            )
    {
        _configuration = configuration;
        _dal = dal;
    }

    @Override
    public UUID handle(UpdateCustomerCommand updateCustomerCommand)
    {
        var model = updateCustomerCommand.getModel();
        var customerId = model.getId();

        var customer = _dal.getById(customerId);

        customer.setFirstName(model.getFirstName());
        customer.setLastName(model.getLastName());

        var email = model.getEmail().toLowerCase(Locale.ROOT);

        updateCustomerEmails(customer, email);

        _dal.update(customer);

        return model.getId();
    }

    private void updateCustomerEmails(CustomerEntity customer, String email)
    {
        var existingEmails = customer.getEmails()
                .stream()
                .filter(e -> e.getEmail().toLowerCase(Locale.ROOT).equals(email));

        if(existingEmails.count() == 0)
        {
            clearEmailPrimaryProperty(customer.getEmails());

            var primaryEmail = new CustomerEmailEntity();
            primaryEmail.setEmail(email);
            primaryEmail.setPrimary(true);

            customer.getEmails().add(primaryEmail);
        }
        else
        {
            var existingEmail = existingEmails.findFirst().get();
            if(false == existingEmail.isPrimary())
            {
                clearEmailPrimaryProperty(customer.getEmails());
                existingEmail.setPrimary(true);
            }
        }
    }

    private void clearEmailPrimaryProperty(List<CustomerEmailEntity> emails)
    {
        emails
                .stream()
                .filter(e -> e.isPrimary())
                .forEach(e ->
                {
                    e.setPrimary(false);
                });
    }
}
