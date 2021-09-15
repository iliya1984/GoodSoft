package com.goodsoft.configurationservice.logic;

import com.goodsoft.configurationservice.entities.ConfigurationSection;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.print.Doc;
import java.util.HashMap;
import java.util.Objects;

public class ConfigurationServiceManager implements IConfigurationServiceManager
{
    @Override
    public ConfigurationSection getMicroserviceConfiguration(String serviceName) {

        var connectionString = new ConnectionString(
                "mongodb://localhost:27017/?authSource=admin"
        );

        var settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
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
               var name = document.getString("name");
               var valueDocument = (Document)document.get("value");
               var value = valueDocument.toJson();

               var section = new ConfigurationSection();
               section.setName(name);
               section.setValue(value);

               return section;
           }
       }

        return null;
    }
}
