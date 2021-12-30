package com.goodsoft.customersservice.dal.implementations.customers;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersDal;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersProducer;
import com.goodsoft.customersservice.dal.implementations.BaseDal;
import com.goodsoft.customersservice.entities.customers.CustomerEmailEntity;
import com.goodsoft.customersservice.entities.customers.CustomerEntity;
import com.goodsoft.customersservice.entities.search.*;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@Service
public class CustomersDal extends BaseDal implements ICustomersDal
{
    private ICustomersProducer _producer;
    private ElasticsearchOperations _elasticsearchOperations;

    private static List<String> searchableFields = new ArrayList<String>()
    {
        {
            add("firstName");
            add("lastName");
        }
    };

    public CustomersDal(
            CustomerServiceConfiguration configuration,
            ICustomersProducer producer,
            ElasticsearchOperations elasticsearchOperations
            )
    {
        super(configuration);

        _producer = producer;
        _elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public CustomerEntity create(CustomerEntity customer) {

        var customerId = java.util.UUID.randomUUID();
        customer.setId(customerId);

        insertCustomer(customer);
        _producer.notifyCustomerCreated(customer);

        return customer;
    }

    @Override
    public SearchResult<CustomerSearchResultItem> search(SearchQuery query)
    {
        try
        {
            var searchQuery = buildQuery(query);
            var searchResponse =
                    _elasticsearchOperations.search(searchQuery, CustomerSearchResultItem.class, IndexCoordinates.of("customers"));

            var items = new ArrayList<CustomerSearchResultItem>();

            var searchResult = new SearchResult<CustomerSearchResultItem>();
            searchResult.setItems(items);

            var searchHists = searchResponse.getSearchHits();
            for(var searchHit : searchHists)
            {
                var item = searchHit.getContent();
                items.add(item);
            }

            return searchResult;
        }
        catch(Exception ex)
        {
            int i = 1;
        }

        return  null;
    }

    private NativeSearchQuery buildQuery(SearchQuery query)
    {
        var queryBuidler = new NativeSearchQueryBuilder();

        for(var filter : query.getFilters())
        {
            var propertyName = filter.getPropertyName();
            var operation = filter.getOperation();
            var value = filter.getValue();

            switch (operation)
            {
                case Equals:
                    queryBuidler
                        .withFilter(QueryBuilders.matchQuery(propertyName, value));
                    break;
                default:
                    break;
            }
        }

        var searchTerm = query.getSearchTerm();
        if(searchTerm != null && false == searchTerm.isEmpty())
        {
            var boolQuery = QueryBuilders.boolQuery();

            for(var field : searchableFields)
            {
                boolQuery.should(QueryBuilders.wildcardQuery(field, "*" + searchTerm + "*"));
            }

            queryBuidler.withFilter(boolQuery);
        }

        var searchQuery =  queryBuidler.build();
        return searchQuery;
    }

    private void insertCustomer(CustomerEntity customer)
    {
        var collection = _database.getCollection("customers");

        Document document = new Document();
        document.append("customerId", customer.getId().toString());
        document.append("firstName", customer.getFirstName());
        document.append("lastName", customer.getLastName());

        var emails = new ArrayList<Document>();

        for (CustomerEmailEntity email : customer.getEmails())
        {
            var emailDocument = new Document();
            emailDocument.append("email", email.getEmail());
            emailDocument.append("isPrimary", email.isPrimary());
            emails.add(emailDocument);
        }
        document.append("emails", emails);

        collection.insertOne(document);
    }
}
