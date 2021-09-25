package com.goodsoft.configurationservice.logic;

import com.goodsoft.configurationservice.dtos.DomainConfiguration;
import com.goodsoft.configurationservice.dtos.DomainConfigurationRequest;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ConfigurationServiceManager implements IConfigurationServiceManager
{
    @Override
    public DomainConfiguration getDomainConfiguration(DomainConfigurationRequest request) {

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

        String domainName = request.getDomainName();
        String key = request.getKey();
        var keyValue = new ObjectId(key);

        var documents = collection.find(Filters.eq("key", keyValue));

       if(documents != null)
       {
           var document = documents.first();
           if(document != null)
           {
               var name = document.getString("domainName");
               var configurationKey = document.getObjectId("key");
               var valueDocument = (Document)document.get("value");
               var value = valueDocument.toJson();

               var section = new DomainConfiguration();
               section.setDomainName(name);
               section.setValue(value);
               section.setKey(configurationKey.toString());

               return section;
           }
       }

        return null;
    }
}
