package com.bobbbaich.hitbtc.service.analysis;

import com.bobbbaich.hitbtc.model.MovingAverage;
import com.bobbbaich.hitbtc.model.Ticker;

import java.util.Collection;
import java.util.Queue;

public interface MovingAverageService {
    Double simpleAvg(Collection<Double> prices);

    Double weightedAvg(Queue<Double> prices);

    Double exponentialAvg(Double price, Double prevEx, Double period);

    MovingAverage movingAverage(Queue<Ticker> tickers);
}