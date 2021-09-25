package com.goodsoft.customersservice.logic.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.configuration.MessageRoutingItem;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.entities.Customer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@RequestHanlder()
public class CreateCustomerCommandHandler implements Command.Handler<CreateCustomerCommand, Customer>
{
    private AmqpTemplate _rabbitTemplate;
    private CustomerServiceConfiguration _configuration;

    public CreateCustomerCommandHandler(AmqpTemplate rabbitMQTemplate, CustomerServiceConfiguration configuration)
    {
        _rabbitTemplate = rabbitMQTemplate;
        _configuration = configuration;
    }

    @Override
    public Customer handle(CreateCustomerCommand createCustomerRequest)
    {


        var model = createCustomerRequest.getModel();

        var customer = new Customer();
        customer.setFirstName(model.getFirstName());
        customer.setLastName(model.getLastName());

        var messageRouting = getMessageRouting();
        if(messageRouting != null)
        {
            var exchange = messageRouting.getExchange();
            var routingKey = messageRouting.getRoutingKey();
            _rabbitTemplate.convertAndSend(exchange, routingKey, customer);
        }

        return customer;
    }

    private MessageRoutingItem getMessageRouting()
    {
        var messageBrokerConfiguration  = _configuration.getMessageBroker();
        if(messageBrokerConfiguration == null)
        {
            return null;
        }

        var routing = messageBrokerConfiguration.getRoutingItems();
        if(routing == null || routing.isEmpty())
        {
            return null;
        }

        var routingItem = routing.stream()
                .filter(ri -> ri.getAction() != null && ri.getAction().toLowerCase(Locale.ROOT).equals("customercreate"))
                .findFirst();

        return routingItem.get();
    }
}
