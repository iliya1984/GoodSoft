package com.goodsoft.customersservice.controllers;

import com.goodsoft.customersservice.entities.Customer;
import com.goodsoft.customersservice.logic.IPipelineFactory;
import com.goodsoft.customersservice.logic.requests.createcustomer.CreateCustomerRequest;
import com.goodsoft.customersservice.logic.requests.getcustomer.GetCustomerRequest;
import com.goodsoft.customersservice.logic.requests.getcustomer.GetCustomerRequestHandler;
import com.goodsoft.customersservice.logic.requests.createcustomer.CreateCustomerModel;
import com.goodsoft.customersservice.logic.requests.createcustomer.CreateCustomerResultModel;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public CreateCustomerResultModel create(@RequestBody CreateCustomerModel model)
    {
        var request = new CreateCustomerRequest();
        request.setModel(model);

        var requestHandler = new GetCustomerRequestHandler();
        var pipeline = _pipelineFactory.getPipeline(requestHandler);

        var result = pipeline.send(request);
        return result;
    }
}
