package com.goodsoft.configurationservice.logic;

import com.goodsoft.configurationservice.entities.ConfigurationSection;
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
    public ConfigurationSection getMicroserviceConfiguration(String serviceName) {

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
        var documents = collection.find(Filters.eq("name", serviceName));

       if(documents != null)
       {
           var document = documents.first();
           if(document != null)
           {
               var id = document.getObjectId("_id");
               var name = document.getString("name");
               var value = document.get("value").toString();

               var section = new ConfigurationSection();
               section.setName(name);
               section.setValue(value);

               return section;
           }
       }

        return null;
    }
}
