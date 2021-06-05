package com.goodsoft.customersservice.controllers;

import com.goodsoft.customersservice.logic.createcustomer.CreateCustomerCommand;
import com.goodsoft.customersservice.logic.getcustomer.GetCustomerQuery;
import com.goodsoft.infra.mediator.errorhandling.ValidationException;
import com.goodsoft.infra.mediator.factory.IPipelineFactory;
import com.goodsoft.infra.microservice.RestService;
import com.goodsoft.interfaces.customers.ICustomersService;
import com.goodsoft.interfaces.customers.models.CreateCustomerModel;
import com.goodsoft.interfaces.customers.models.GetCustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/customers")
public class CustomerController extends RestService implements ICustomersService
{
    private IPipelineFactory _pipelineFactory;

    public CustomerController(IPipelineFactory pipelineFactory)
    {
        _pipelineFactory = pipelineFactory;
    }

    @GetMapping(value = "/{id}")
    public GetCustomerResponse findById(@PathVariable("id") Long id) {

        var request = new GetCustomerQuery();
        request.setCustomerId(id);

        var pipeline = _pipelineFactory.getPipeline(request);
        var customer = pipeline.send(request);

        var response  = new GetCustomerResponse();
        response.setCustomer(customer);

        return response;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody CreateCustomerModel model)
    {
        try
        {
            var request = new CreateCustomerCommand();
            request.setModel(model);

            var pipeline = _pipelineFactory.getPipeline(request);
            var customer = pipeline.send(request);

            return new ResponseEntity(customer, HttpStatus.CREATED);
        }
        catch(Exception ex)
        {
            return exceptionResponse(ex);
        }
    }
}
