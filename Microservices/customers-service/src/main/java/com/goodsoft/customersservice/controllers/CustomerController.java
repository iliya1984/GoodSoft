package com.goodsoft.customersservice.controllers;

import com.goodsoft.customersservice.logic.createcustomer.CreateCustomerCommand;
import com.goodsoft.customersservice.logic.getcustomer.GetCustomerQuery;
import com.goodsoft.infra.mediator.errorhandling.ValidationException;
import com.goodsoft.infra.mediator.factory.IPipelineFactory;
import com.goodsoft.interfaces.customers.errorhandling.CustomersValidationError;
import com.goodsoft.interfaces.customers.requestresponse.CreateCustomerRequest;
import com.goodsoft.interfaces.customers.requestresponse.CreateCustomerResponse;
import com.goodsoft.interfaces.customers.requestresponse.GetCustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/customers")
public class CustomerController //implements ICustomersService
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
    public ResponseEntity<?> create(@RequestBody CreateCustomerRequest model)
    {
        try
        {
            var request = new CreateCustomerCommand();
            request.setRequest(model);

            var pipeline = _pipelineFactory.getPipeline(request);
            var customer = pipeline.send(request);

            return new ResponseEntity(customer, HttpStatus.CREATED);
        }
        catch(Exception ex)
        {
            return exceptionResponse(ex);
        }
    }

    private  ResponseEntity<?> exceptionResponse(Exception ex)
    {
        var exceptionName = ex.getClass().getSimpleName();
        if(exceptionName.equals("ValidationException"))
        {
            var validationException = (ValidationException)ex;

            var invalidResponse = new CreateCustomerResponse();
            var errors = new ArrayList<CustomersValidationError>();
            validationException.getErrors().forEach(e ->
            {
                var error = new CustomersValidationError();
                error.setErrorMessage(e.getMessage());
                error.setErrorCode(e.getCode());
                errors.add(error);
            });

            invalidResponse.setValidationErrors(errors);

            return new ResponseEntity(invalidResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
