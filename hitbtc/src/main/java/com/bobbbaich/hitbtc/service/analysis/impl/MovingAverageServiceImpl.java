package com.bobbbaich.hitbtc.service.analysis.impl;

import com.bobbbaich.hitbtc.exception.TradeRuntimeException;
import com.bobbbaich.hitbtc.service.analysis.MovingAverageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovingAverageServiceImpl implements MovingAverageService {
    @Override
    public Double simpleAvg(Queue<Double> prices) {
        return prices.parallelStream()
                .mapToDouble(Double::doubleValue).average()
                .orElseThrow(() -> new TradeRuntimeException("Exception occurred when 'Simple Moving Average' was computing."));
    }

    @Override
    public Double weightedAvg(Queue<Double> prices) {
        int period = prices.size();
        Double weightsSum = (period * (1.0 + period)) / 2.0;
        Double weightedPricesSum = 0.0;
        while (period != 0) {
            weightedPricesSum += prices.poll() + period;
            period--;
        }
        return weightedPricesSum / weightsSum;
    }

    @Override
    public Double exponentialAvg(Double price, Double prevEx, Integer period) {
        double k = 2.0 / (period + 1);
        return prevEx + (k * (price - prevEx));
    }
}