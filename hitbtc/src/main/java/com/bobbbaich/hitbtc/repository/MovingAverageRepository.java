package com.bobbbaich.hitbtc.repository;

import com.bobbbaich.hitbtc.model.MovingAverage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovingAverageRepository extends MongoRepository<MovingAverage, String> {
}
