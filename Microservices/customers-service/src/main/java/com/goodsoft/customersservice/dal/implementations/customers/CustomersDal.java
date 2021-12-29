package com.goodsoft.customersservice.dal.implementations.customers;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersDal;
import com.goodsoft.customersservice.dal.abstractions.customers.ICustomersProducer;
import com.goodsoft.customersservice.dal.implementations.BaseDal;
import com.goodsoft.customersservice.entities.customers.CustomerEmailEntity;
import com.goodsoft.customersservice.entities.customers.CustomerEntity;
import com.goodsoft.customersservice.entities.search.CustomerSearchResultItem;
import com.goodsoft.customersservice.entities.search.SearchQuery;
import com.goodsoft.customersservice.entities.search.SearchResult;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.bson.Document;

import java.util.ArrayList;

import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@Service
public class CustomersDal extends BaseDal implements ICustomersDal
{
    private ICustomersProducer _producer;
    private ElasticsearchOperations _elasticsearchOperations;

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
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(regexpQuery("firstName", ".*iliya.*"))
                .build();

        var customers =
                _elasticsearchOperations.search(searchQuery, CustomerSearchResultItem.class);

        var searchResult = new SearchResult<CustomerSearchResultItem>();

        //TODO: map search hists to customer search result items

       return searchResult;
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
