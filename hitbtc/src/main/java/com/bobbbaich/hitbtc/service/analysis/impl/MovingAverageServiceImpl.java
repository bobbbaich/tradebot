package com.bobbbaich.hitbtc.service.analysis.impl;

import com.bobbbaich.hitbtc.exception.TradeRuntimeException;
import com.bobbbaich.hitbtc.model.MovingAverage;
import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.service.analysis.MovingAverageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import static java.util.stream.Collectors.toCollection;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovingAverageServiceImpl implements MovingAverageService {
    @Override
    public Double simpleAvg(Collection<Double> prices) {
        return prices.parallelStream()
                .mapToDouble(Double::doubleValue).average()
                .orElseThrow(() -> new TradeRuntimeException("Exception occurred when Simple Moving Average was computing."));
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
    public Double exponentialAvg(Double price, Double prevEx, Double period) {
        double k = 2 / (period + 1);
        return prevEx + (k * (price - prevEx));
    }

    @Override
    public MovingAverage movingAverage(Queue<Ticker> tickers) {
        Queue<Double> prices = tickers
                .stream().mapToDouble(Ticker::getLast)
                .boxed().collect(toCollection(LinkedList::new));

        Double simpleAvg = simpleAvg(prices);
        Double weightedAvg = weightedAvg(prices);

        MovingAverage movingAverage = new MovingAverage();

        movingAverage.setSimple(simpleAvg);
        movingAverage.setWeighted(weightedAvg);

        return movingAverage;
    }
}