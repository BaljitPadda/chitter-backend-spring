package com.chitterchallengespring.demo;

import com.chitterchallengespring.demo.model.Peep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Configuration
public class TestMongoConfig {

    private static final String collectionName = "peeps_";

    @Bean
    public static MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/peeps_test"));
    }
    //Mongo template is a representation of a database

    public static void clearCollection() {
        System.out.println("Deleting existing peeps");
        mongoTemplate().remove(new Query(), collectionName);
    }

    public static void repopulateCollection(List<Peep> peeps) {
        System.out.println("Creating peeps");
        System.out.println("Inserting peeps");
        mongoTemplate().insert(peeps, collectionName);
    }

}
