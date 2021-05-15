package com.goodsoft.customersservice.controllers;

import com.goodsoft.customersservice.entities.Customer;
import com.goodsoft.customersservice.entities.CustomerEmail;
import com.goodsoft.customersservice.logic.ICustomersManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    private ICustomersManager _manager;

    public CustomerController(ICustomersManager manager)
    {
        _manager = manager;
    }

    @GetMapping(value = "/{id}")
    public List<Customer> findById(@PathVariable("id") Long id) {

        var customers = _manager.GetById(id);
        return customers;
    }
}
