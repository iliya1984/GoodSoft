package com.goodsoft.customersservice.controllers;

import com.goodsoft.customersservice.logic.createcustomer.CreateCustomerCommand;
import com.goodsoft.customersservice.logic.getcustomer.GetCustomerQuery;
import com.goodsoft.infra.mediator.errorhandling.ValidationException;
import com.goodsoft.infra.mediator.factory.IPipelineFactory;
import com.goodsoft.interfaces.customers.ICustomersService;
import com.goodsoft.interfaces.customers.models.CreateCustomerRequest;
import com.goodsoft.interfaces.customers.models.CreateCustomerResponse;
import com.goodsoft.interfaces.customers.models.GetCustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/customers")
public class CustomerController implements ICustomersService
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
    public ResponseEntity<CreateCustomerResponse> create(@RequestBody CreateCustomerRequest model)
    {
        try
        {
            var request = new CreateCustomerCommand();
            request.setRequest(model);

            var pipeline = _pipelineFactory.getPipeline(request);

            var customer = pipeline.send(request);

            var response = new CreateCustomerResponse();

            //TODO: set customer
            //response.setCustomer(customer);

            return new ResponseEntity<CreateCustomerResponse>(response, HttpStatus.OK);

        }
        catch(Exception ex)
        {
            var exceptionName = ex.getClass().getSimpleName();
            if(exceptionName.equals("ValidationException"))
            {
                var validationException = (ValidationException)ex;
                return new ResponseEntity<Collection<Error>>(validationException.getErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }
}
