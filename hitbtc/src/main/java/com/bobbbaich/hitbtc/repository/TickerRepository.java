package com.bobbbaich.hitbtc.repository;

import com.bobbbaich.hitbtc.model.Ticker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends MongoRepository<Ticker, String> {
}
