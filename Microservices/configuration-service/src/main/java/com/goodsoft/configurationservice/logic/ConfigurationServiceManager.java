package com.goodsoft.configurationservice.logic;

import com.goodsoft.configurationservice.entities.DomainConfiguration;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class ConfigurationServiceManager implements IConfigurationServiceManager
{
    @Override
    public DomainConfiguration getMicroserviceConfiguration(String domainName) {

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
        var documents = collection.find(Filters.eq("domainName", domainName));

       if(documents != null)
       {
           var document = documents.first();
           if(document != null)
           {
               var name = document.getString("domainName");
               var valueDocument = (Document)document.get("value");
               var value = valueDocument.toJson();

               var section = new DomainConfiguration();
               section.setDomainName(name);
               section.setValue(value);

               return section;
           }
       }

        return null;
    }
}
