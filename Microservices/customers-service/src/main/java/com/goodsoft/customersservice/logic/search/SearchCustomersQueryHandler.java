package com.goodsoft.customersservice.logic.search;

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

    public SearchCustomersQueryHandler(ICustomersDal dal)
    {
        _dal = dal;
    }

    @Override
    public SearchResultModel<CustomerSearchResultItemModel> handle(SearchCustomersQuery request)
    {
        var query = mapRequestToSearchQuery(request);

        var result = _dal.search(query);

        var model = mapToSearchResultModel(result);

        return model;
    }

    public SearchResultModel<CustomerSearchResultItemModel> mapToSearchResultModel(SearchResult<CustomerSearchResultItem> searchResult)
    {
        var itemModels = new ArrayList<CustomerSearchResultItemModel>();

        var model = new SearchResultModel<CustomerSearchResultItemModel>();
        model.setItems(itemModels);

        var searchItems = searchResult.getItems();
        if(searchItems != null && searchItems.stream().count() > 0)
        {
            for(var searchItem : searchItems)
            {
                var itemModel = mapToSearchResultItemModel(searchItem);
                itemModels.add(itemModel);
            }
        }

        return model;
    }

    private CustomerSearchResultItemModel mapToSearchResultItemModel(CustomerSearchResultItem item)
    {
        var model = new CustomerSearchResultItemModel();

        model.setId(item.getId());
        model.setFirstName(item.getFirstName());
        model.setLastName(item.getLastName());
        model.setEmail(item.getPrimaryEmail());
        model.setPhone(item.getPrimaryPhone());

        return model;
    }

    private SearchQuery mapRequestToSearchQuery(SearchCustomersQuery request)
    {
        var query = new SearchQuery();

        query.setSearchTerm(request.getSearchTerm());
        query.setSkip(request.getSkip());
        query.setTake(request.getTake());

        var filters = new ArrayList<SearchFilter>();

        query.setFilters(filters);

        var allFiltersString = request.getFilters();
        String[] filterItemStrings = allFiltersString.split(",");

        for(var filterItemString : filterItemStrings)
        {
            String[] filterInfo = filterItemString.split("_");

            if(filterInfo.length == 3)
            {
                var filter = new SearchFilter();

                filter.setPropertyName(filterInfo[0]);
                filter.setValue(filterInfo[2]);

                var filterOperation = mapFilterOperation(filterInfo[1]);
                filter.setOperation(filterOperation);

                filters.add(filter);
            }
        }

        return query;
    }

    private FilterOperation mapFilterOperation(String filterOperation)
    {
        switch (filterOperation)
        {
            case "eq":
                return FilterOperation.Equals;
            case "neq":
                return FilterOperation.NotEquals;
            case "greeq":
                return FilterOperation.GreaterOrEquals;
            case "lseq":
                return FilterOperation.LessOrEquals;
            default:
                return FilterOperation.None;
        }
    }

}
