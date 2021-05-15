package com.goodsoft.customersservice.controllers;

import com.goodsoft.customersservice.entities.Customer;
import com.goodsoft.customersservice.entities.CustomerEmail;
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
    @GetMapping(value = "/{id}")
    public Customer findById(@PathVariable("id") Long id) {

        var customer = new Customer();
        customer.setFirstName("Iliya");
        customer.setLastName("Nahshan");

        var primaryEmail = new CustomerEmail();
        primaryEmail.setEmail("iliya.nahshan@gmail.com");
        primaryEmail.setPrimary(true);

        var emails = new ArrayList<CustomerEmail>();
        emails.add(primaryEmail);
        customer.setEmails(emails);

        return customer;
    }
}
