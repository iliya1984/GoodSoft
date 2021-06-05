package com.goodsoft.configurationservice.logic;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.HashMap;
import java.util.Objects;

public class ConfigurationServiceManager implements IConfigurationServiceManager
{
    @Override
    public Object getMicroserviceConfiguration(String serviceName) {

        var connString = new ConnectionString(
                "mongodb://localhost:27017/?authSource=admin"
        );
        var settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        var mongoClient = MongoClients.create(settings);
        var database = mongoClient.getDatabase("configuration");

        var collection = database.getCollection("microserviceConfiguration");
        var documents = collection.find(Filters.eq("serviceName", serviceName));

       if(documents != null)
       {
           var document = documents.first();
           if(document != null)
           {
               var configurationJson = document.toJson();
               return configurationJson;
           }
       }

        return null;
    }
}
