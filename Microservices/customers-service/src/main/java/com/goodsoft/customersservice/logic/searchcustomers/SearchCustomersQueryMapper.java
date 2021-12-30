package com.goodsoft.customersservice.logic.searchcustomers;

import com.goodsoft.customersservice.entities.search.*;
import com.goodsoft.interfaces.customers.models.search.CustomerSearchResultItemModel;
import com.goodsoft.interfaces.customers.models.search.SearchResultModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SearchCustomersQueryMapper implements ISearchCustomersQueryMapper
{
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

    public SearchQuery mapRequestToSearchQuery(SearchCustomersQuery request)
    {
        var query = new SearchQuery();

        query.setSearchTerm(request.getSearchTerm());
        query.setSkip(request.getSkip());
        query.setTake(request.getTake());

        var sortProperty = request.getSortProperty();
        if(sortProperty != null && false == sortProperty.isEmpty())
        {
            var sort = new SortOptions();
            sort.setPropertyName(sortProperty);
            sort.setSortOrder(mapSortOrder(request.getSortOrder()));

            query.setSortOptions(sort);
        }

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

    private SortOrder mapSortOrder(String sortOrder)
    {
        if(sortOrder == null)
        {
            return SortOrder.Descending;
        }

        switch (sortOrder)
        {
            case "asc":
                return SortOrder.Ascending;
            case "desc":
            default:
                return SortOrder.Descending;
        }
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
