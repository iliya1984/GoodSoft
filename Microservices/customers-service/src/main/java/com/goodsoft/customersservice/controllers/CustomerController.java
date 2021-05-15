package com.goodsoft.customersservice.controllers;

import com.goodsoft.customersservice.entities.Customer;
import com.goodsoft.customersservice.logic.IPipelineFactory;
import com.goodsoft.customersservice.logic.requests.getcustomer.GetCustomerRequest;
import com.goodsoft.customersservice.logic.requests.getcustomer.GetCustomerRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    private IPipelineFactory _pipelineFactory;

    public CustomerController(IPipelineFactory pipelineFactory)
    {
        _pipelineFactory = pipelineFactory;
    }

    @GetMapping(value = "/{id}")
    public Customer findById(@PathVariable("id") Long id) {

        var request = new GetCustomerRequest();
        request.setCustomerId(id);

        var pipeline = _pipelineFactory.getPipeline(new GetCustomerRequestHandler());
        var customer = pipeline.send(request);

        return customer;
    }
}
