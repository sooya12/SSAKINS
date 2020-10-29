package com.ssafy.ssakins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class SsakinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsakinsApplication.class, args);
    }

}
