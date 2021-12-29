package com.goodsoft.customersservice.dal.abstractions.customers;

import com.goodsoft.customersservice.entities.customers.CustomerEntity;
import com.goodsoft.customersservice.entities.search.CustomerSearchResultItem;
import com.goodsoft.customersservice.entities.search.SearchQuery;
import com.goodsoft.customersservice.entities.search.SearchResult;

public interface ICustomersDal
{
    CustomerEntity create(CustomerEntity customer);
    SearchResult<CustomerSearchResultItem> search(SearchQuery query);
}
