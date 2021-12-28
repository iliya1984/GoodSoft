package com.goodsoft.customersservice.logic.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.configuration.MessageRoutingItem;
import com.goodsoft.customersservice.dal.abstractions.ICustomersDal;
import com.goodsoft.customersservice.dal.implementations.CustomersDal;
import com.goodsoft.customersservice.entities.CustomerEmailEntity;
import com.goodsoft.customersservice.entities.CustomerEntity;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.entities.Customer;
import com.goodsoft.interfaces.customers.entities.CustomerEmail;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.Locale;
import java.util.UUID;

@RequestHanlder()
public class CreateCustomerCommandHandler implements Command.Handler<CreateCustomerCommand, UUID>
{
    private final String Action = "customercreate";

    private AmqpTemplate _rabbitTemplate;
    private CustomerServiceConfiguration _configuration;
    private ICustomersDal _dal;

    public CreateCustomerCommandHandler
            (
                    AmqpTemplate rabbitMQTemplate,
                    ICustomersDal dal,
                    CustomerServiceConfiguration configuration
            )
    {
        _rabbitTemplate = rabbitMQTemplate;
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

        var result = _dal.Create(customer);


        var messageRouting = _configuration.findMessageRoutingByName(Action);
        if(messageRouting != null)
        {
            var exchange = messageRouting.getExchange();
            var routingKey = messageRouting.getRoutingKey();
            _rabbitTemplate.convertAndSend(exchange, routingKey, customer);
        }

        return result.getId();
    }


}
