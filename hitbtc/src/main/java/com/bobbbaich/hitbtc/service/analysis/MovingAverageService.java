package com.bobbbaich.hitbtc.service.analysis;

import java.util.Queue;

public interface MovingAverageService {
    Double simpleAvg(Queue<Double> prices);

    Double weightedAvg(Queue<Double> prices);

    Double exponentialAvg(Double price, Double prevEx, Integer period);
}