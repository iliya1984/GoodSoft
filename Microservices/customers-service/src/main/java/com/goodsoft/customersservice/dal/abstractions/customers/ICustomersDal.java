package com.goodsoft.customersservice.dal.abstractions.customers;

import com.goodsoft.customersservice.entities.customers.CustomerEntity;
import com.goodsoft.customersservice.entities.search.CustomerSearchResultItem;
import com.goodsoft.customersservice.entities.search.SearchQuery;
import com.goodsoft.customersservice.entities.search.SearchResult;

import java.util.UUID;

public interface ICustomersDal
{
    CustomerEntity create(CustomerEntity customer);
    void update(CustomerEntity customer);
    CustomerEntity getById(UUID id);
    SearchResult<CustomerSearchResultItem> search(SearchQuery query);
}
