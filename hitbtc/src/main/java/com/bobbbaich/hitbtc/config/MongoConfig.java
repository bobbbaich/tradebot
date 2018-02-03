package com.bobbbaich.hitbtc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.bobbbaich.hitbtc.repository")
public class MongoConfig {
}
