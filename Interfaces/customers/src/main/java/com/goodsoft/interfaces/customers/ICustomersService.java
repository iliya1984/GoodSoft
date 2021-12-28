package com.goodsoft.interfaces.customers;

import com.goodsoft.interfaces.customers.models.customers.CreateCustomerModel;
import com.goodsoft.interfaces.customers.models.customers.GetCustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICustomersService
{
    ResponseEntity<?> create(@RequestBody CreateCustomerModel model);
    GetCustomerResponse findById(@PathVariable("id") Long id);
}
