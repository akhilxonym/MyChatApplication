package org.mychat.backend.config.Mongo;


import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override  
    public MongoClient mongoClient(){
       // MongoClientURI mongoClientURI=new MongoClientURI("mongodb+srv://Akhil:Newlife0077@cluster0.dncu1.azure.mongodb.net/tryg1?retryWrites=true&w=majority");
        MongoClient client = MongoClients.create(new ConnectionString( "mongodb+srv://Akhil:Newlife0077@cluster0.dncu1.azure.mongodb.net/tryg1?retryWrites=true&w=majority"));
        return client; 
    
    }
 
    @Override
    protected String getDatabaseName() {
        // TODO Auto-generated method stub
        return "tryg1";
    }
 
}
