package com.bobbbaich.hitbtc.config;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@Configuration
@EnableMongoRepositories
public class MongoConfig {
    @Bean
    public MongoItemWriter<Candle> mongoCandleWriter(MongoOperations template) {
        MongoItemWriter<Candle> itemWriter = new MongoItemWriter<>();
        itemWriter.setTemplate(template);
        return itemWriter;
    }

    @Bean
    public MongoItemWriter<Ticker> mongoTickerWriter(MongoOperations template) {
        MongoItemWriter<Ticker> itemWriter = new MongoItemWriter<>();
        itemWriter.setTemplate(template);
        return itemWriter;
    }
}
