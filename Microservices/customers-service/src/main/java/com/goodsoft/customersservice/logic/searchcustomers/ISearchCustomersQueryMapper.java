package com.goodsoft.customersservice.logic.searchcustomers;

import com.goodsoft.customersservice.entities.search.CustomerSearchResultItem;
import com.goodsoft.customersservice.entities.search.SearchQuery;
import com.goodsoft.customersservice.entities.search.SearchResult;
import com.goodsoft.interfaces.customers.models.search.CustomerSearchResultItemModel;
import com.goodsoft.interfaces.customers.models.search.SearchResultModel;

public interface ISearchCustomersQueryMapper
{
    SearchQuery mapRequestToSearchQuery(SearchCustomersQuery request);
    SearchResultModel<CustomerSearchResultItemModel> mapToSearchResultModel(SearchResult<CustomerSearchResultItem> searchResult);
}
