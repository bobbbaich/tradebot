package com.bobbbaich.hitbtc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.config.EnableReactor;

import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.matchAll;

@Configuration
@EnableReactor
@Slf4j
public class ReactorConfig {

    private EventBus eventBus;
    private MongoTemplate mongo;

    @PostConstruct
    private void postConstruct() {
        log.debug("postConstruct");
        eventBus.on(matchAll(), (Event<?> consumer) -> {
            Object data = consumer.getData();
            mongo.insert(data);
        });
    }

    @Autowired
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Autowired
    public void setMongo(MongoTemplate mongo) {
        this.mongo = mongo;
    }
}