package com.goodsoft.customersservice.logic;

import com.goodsoft.customersservice.entities.Customer;
import com.goodsoft.customersservice.entities.CustomerEmail;

import java.util.ArrayList;
import java.util.List;

public class CustomersManager implements ICustomersManager
{
    @Override
    public List<Customer> GetById(Long customerId) {

        var customer = new Customer();
        customer.setFirstName("Iliya");
        customer.setLastName("Nahshan");

        var primaryEmail = new CustomerEmail();
        primaryEmail.setEmail("iliya.nahshan@gmail.com");
        primaryEmail.setPrimary(true);

        var emails = new ArrayList<CustomerEmail>();
        emails.add(primaryEmail);
        customer.setEmails(emails);

       var customers = new ArrayList<Customer>();
       customers.add(customer);

        return customers;
    }
}
