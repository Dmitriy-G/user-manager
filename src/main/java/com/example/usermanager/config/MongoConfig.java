package com.example.usermanager.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig  {

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabaseName;

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(mongoHost);
    }

    @Bean
    protected MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoDatabaseName);
    }
}
