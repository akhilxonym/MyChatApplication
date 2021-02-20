package org.mychat.backend.service.mongo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoRepository extends MongoMapper{
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoCollection<Document> getCollection(MongoTemplate template,String collectionName){
        return template.getCollection(collectionName);
    }

    public <T> List<T> find(String collectionName, DBObject query, Class<T> castTo ){
        List<T> output=new ArrayList<>();
        MongoCollection<Document> collection=mongoTemplate.getCollection(collectionName);
        collection.find(getDocument(query)).iterator()
        .forEachRemaining(document-> output.add(getMappedObject(document, castTo)));

        return output;
    }

    public <T> List<T> findWithSkip(String collectionName, DBObject query, Class<T> castTo,int skip ){
        List<T> output=new ArrayList<>();
        MongoCollection<Document> collection=mongoTemplate.getCollection(collectionName);
        collection.find().skip(skip).limit(3).iterator()
        .forEachRemaining(document-> output.add(getMappedObject(document, castTo)));

        return output;
    }

    public <T> T findOne(String collectionName, DBObject query, Class<T> castTo ){
        MongoCollection<Document> collection=mongoTemplate.getCollection(collectionName);
        Document document=collection.find(getDocument(query)).first();
        return document!=null ? getMappedObject(document, castTo):null;
    }

    public void save(String collectionName,Object document){
        mongoTemplate.save(document, collectionName);
    }

    public <T> List<T> aggregate(String collectionName,List<DBObject> pipeline, Class<T> castTo){
        List<T> output=new ArrayList<>();
        MongoCollection<Document> collection= mongoTemplate.getCollection(collectionName);
        collection.aggregate(getDocuments(pipeline)).iterator().forEachRemaining(document -> output.add(getMappedObject(document,castTo)));
        return output;

    }
}
