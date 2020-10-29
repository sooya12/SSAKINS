package com.ssafy.ssakins.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    public @Bean MongoClient mongoClient() {
        return MongoClients.create("mongodb://k3a201.p.ssafy.io:3306");
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "ssakins");
    }
}
