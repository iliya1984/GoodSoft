package com.goodsoft.customersservice.logic.search;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.entities.search.CustomerSearchResultItem;
import com.goodsoft.customersservice.entities.search.SearchResult;
import com.goodsoft.interfaces.customers.models.search.CustomerSearchResultItemModel;
import com.goodsoft.interfaces.customers.models.search.SearchResultModel;
import lombok.Getter;
import lombok.Setter;

public class SearchCustomersQuery implements Command<SearchResultModel<CustomerSearchResultItemModel>>
{
    @Getter
    @Setter
    private String searchTerm;

    @Getter
    @Setter
    private String filters;

    @Getter
    @Setter
    private int skip;

    @Getter
    @Setter
    private int take;
}
