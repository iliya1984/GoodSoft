package com.goodsoft.customersservice.logic.searchcustomers;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersDal;
import com.goodsoft.customersservice.entities.search.*;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.interfaces.customers.models.search.CustomerSearchResultItemModel;
import com.goodsoft.interfaces.customers.models.search.SearchResultModel;

import java.util.ArrayList;

@RequestHanlder()
public class SearchCustomersQueryHandler implements Command.Handler<SearchCustomersQuery, SearchResultModel<CustomerSearchResultItemModel>>
{
    private ICustomersDal _dal;
    private ISearchCustomersQueryMapper _mapper;

    public SearchCustomersQueryHandler(ISearchCustomersQueryMapper mapper, ICustomersDal dal)
    {
        _mapper = mapper;
        _dal = dal;
    }

    @Override
    public SearchResultModel<CustomerSearchResultItemModel> handle(SearchCustomersQuery request)
    {
        var query = _mapper.mapRequestToSearchQuery(request);

        var result = _dal.search(query);

        var model = _mapper.mapToSearchResultModel(result);

        return model;
    }



}
