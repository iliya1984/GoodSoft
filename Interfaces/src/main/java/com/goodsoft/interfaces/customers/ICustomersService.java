package com.goodsoft.interfaces.customers;

import com.goodsoft.interfaces.customers.models.CreateCustomerRequest;
import com.goodsoft.interfaces.customers.models.CreateCustomerResponse;
import com.goodsoft.interfaces.customers.models.GetCustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICustomersService
{
    ResponseEntity<CreateCustomerResponse> create(@RequestBody CreateCustomerRequest model);
    GetCustomerResponse findById(@PathVariable("id") Long id);
}
