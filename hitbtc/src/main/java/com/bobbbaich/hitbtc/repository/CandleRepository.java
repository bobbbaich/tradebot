package com.bobbbaich.hitbtc.repository;

import com.bobbbaich.hitbtc.model.Candle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandleRepository extends MongoRepository<Candle, String> {
}
