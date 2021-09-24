package com.goodsoft.customersservice.logic.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.entities.Customer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;

@RequestHanlder()
public class CreateCustomerCommandHandler implements Command.Handler<CreateCustomerCommand, Customer>
{
    private AmqpTemplate _rabbitTemplate;

    public CreateCustomerCommandHandler(AmqpTemplate rabbitMQTemplate)
    {
        _rabbitTemplate = rabbitMQTemplate;
    }

    @Override
    public Customer handle(CreateCustomerCommand createCustomerRequest)
    {
        var model = createCustomerRequest.getModel();

        var customer = new Customer();
        customer.setFirstName(model.getFirstName());
        customer.setLastName(model.getLastName());

        var exchange = "customers";
        var routingKey = "contomer.created.event";
        
        _rabbitTemplate.convertAndSend(exchange, routingKey, customer);

        return customer;
    }
}
