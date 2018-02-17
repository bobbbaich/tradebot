package com.bobbbaich.hitbtc.service.reactor;

import com.bobbbaich.hitbtc.model.MovingAverage;
import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.repository.MovingAverageRepository;
import com.bobbbaich.hitbtc.service.analysis.MovingAverageService;
import com.bobbbaich.hitbtc.service.analysis.TrendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;
import reactor.spring.context.annotation.SelectorType;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToDoubleFunction;

import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.SELECTOR_AVERAGE;
import static java.util.stream.Collectors.toCollection;

@Slf4j
@Consumer
@RequiredArgsConstructor
public class MovingAverageProcessor {
    private final EventBus eventBus;
    private final TrendService trendService;
    private final MovingAverageService movingAverageService;
    private final MovingAverageRepository repository;
    //  Ticker map where key is s symbol
    private Map<String, Double> exponentialAvgBySymbol = new ConcurrentHashMap<>();

    @Transactional
    @Selector(value = SELECTOR_AVERAGE, type = SelectorType.REGEX)
    public void movingAverage(Event<Queue<Ticker>> event) {
        Queue<Ticker> tickers = event.getData();
        Queue<Double> prices = getPrices(tickers, Ticker::getLast);

        Ticker lastTicker = tickers.peek();
        Integer period = prices.size();
        Double lastPrice = prices.peek();

        Double simpleAvg = movingAverageService.simpleAvg(prices);
        Double prevExponential = getPrevExponential(lastTicker, simpleAvg);
        Double exponentialAvg = movingAverageService.exponentialAvg(lastPrice, prevExponential, period);
        Double weightedAvg = movingAverageService.weightedAvg(prices);

        updateExponentialAvg(lastTicker, exponentialAvg);

        MovingAverage movingAverage = lastTicker.getMovingAverage();
        movingAverage.setPeriod(period);
        movingAverage.setSimple(simpleAvg);
        movingAverage.setWeighted(weightedAvg);
        movingAverage.setExponential(exponentialAvg);

        repository.save(movingAverage);

        trendService.movingAvgSignal(movingAverage);
    }

    private LinkedList<Double> getPrices(Queue<Ticker> tickers, ToDoubleFunction<Ticker> doubleFunction) {
        return tickers.stream().mapToDouble(doubleFunction)
                .boxed().collect(toCollection(LinkedList::new));
    }

    private void updateExponentialAvg(Ticker lastTicker, Double exponentialAvg) {
        exponentialAvgBySymbol.put(lastTicker.getSymbol(), exponentialAvg);
    }

    private Double getPrevExponential(Ticker ticker, Double simpleAvg) {
        Assert.notNull(ticker, "Ticker cannot be null!");
        return exponentialAvgBySymbol.getOrDefault(ticker.getSymbol(), simpleAvg);
    }
}