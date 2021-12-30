package com.goodsoft.customersservice.logic.searchcustomers;

import an.awesome.pipelinr.Command;
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
    private Integer skip;

    @Getter
    @Setter
    private Integer take;

    @Getter
    @Setter
    private String sortProperty;

    @Getter
    @Setter
    private String sortOrder;
}
