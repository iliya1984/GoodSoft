package com.goodsoft.interfaces.customers;

import com.goodsoft.interfaces.customers.models.CreateCustomerModel;
import com.goodsoft.interfaces.customers.models.CreateCustomerResultModel;
import com.goodsoft.interfaces.customers.models.CustomerModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICustomersService
{
    ResponseEntity<CreateCustomerResultModel> create(@RequestBody CreateCustomerModel model);
    CustomerModel findById(@PathVariable("id") Long id);
}
