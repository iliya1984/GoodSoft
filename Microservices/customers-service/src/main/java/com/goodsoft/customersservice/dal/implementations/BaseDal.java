package com.goodsoft.customersservice.dal.implementations;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.SneakyThrows;

import javax.naming.ConfigurationException;

public abstract class BaseDal
{
    protected CustomerServiceConfiguration _configuration;
    protected MongoDatabase _database;

    @SneakyThrows
    public BaseDal(CustomerServiceConfiguration configuration)
    {
        _configuration = configuration;

        initializeDal();
    }

    private void initializeDal() throws ConfigurationException
    {
        var datasource = _configuration.getDatasource();
        if(datasource == null || false == datasource.getDatasourceType().equals("MongoDB"))
        {
            throw new ConfigurationException("Unable to proceed with DAL base settings, invalid datasource type");
        }

        var connectionStringValue = datasource.getConnectionString();

        if(connectionStringValue == null ||  connectionStringValue.isEmpty())
        {
            throw new ConfigurationException("Unable to proceed with DAL base settings, connection string not set");
        }

        var user = datasource.getUser();
        if(user == null ||  user.isEmpty())
        {
            throw new ConfigurationException("Unable to proceed with DAL base settings, user not set");
        }

        var connectionString = new ConnectionString(
                connectionStringValue + "?authSource=" + user
        );

        var settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(true)
                .build();

        var mongoClient = MongoClients.create(settings);
        _database = mongoClient.getDatabase("customers");
    }
}
