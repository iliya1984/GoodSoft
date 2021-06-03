package com.goodsoft.customersservice.controllers;

import com.goodsoft.customersservice.entities.Customer;
import com.goodsoft.customersservice.logic.requests.createcustomer.CreateCustomerRequest;
import com.goodsoft.customersservice.logic.requests.getcustomer.GetCustomerRequest;
import com.goodsoft.customersservice.logic.requests.createcustomer.CreateCustomerModel;
import com.goodsoft.customersservice.logic.requests.createcustomer.CreateCustomerResultModel;
import com.goodsoft.infra.mediator.factory.IPipelineFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        var pipeline = _pipelineFactory.getPipeline(request);
        var customer = pipeline.send(request);

        return customer;
    }

    @PostMapping()
    public ResponseEntity<CreateCustomerResultModel> create(@RequestBody CreateCustomerModel model)
    {
        try
        {
            var request = new CreateCustomerRequest();
            request.setModel(model);

            var pipeline = _pipelineFactory.getPipeline(request);

            var result = pipeline.send(request);
            return new ResponseEntity<>(result, HttpStatus.OK);

        }
        catch(Exception ex)
        {
            var exceptionName = ex.getClass().getSimpleName();
            if(exceptionName.equals("CustomerValidationException"))
            {
                return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }
}
