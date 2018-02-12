package com.bobbbaich.hitbtc.service.reactor;

import com.bobbbaich.hitbtc.model.MovingAverage;
import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.repository.TickerRepository;
import com.bobbbaich.hitbtc.service.analysis.MovingAverageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;
import reactor.spring.context.annotation.SelectorType;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.SELECTOR_AVERAGE;
import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.SELECTOR_TICKER_TYPE;

@Slf4j
@Consumer
@RequiredArgsConstructor
public class TickerProcessor {
    public static final int TICKER_SIZE = 13;

    private final EventBus eventBus;
    private final TickerRepository repository;
    private final MovingAverageService movingAverageService;

    //  Ticker map where key is s symbol
    private Map<String, Queue<Ticker>> framesBySymbol = new ConcurrentHashMap<>();

    @Transactional
    @Selector(value = SELECTOR_TICKER_TYPE, type = SelectorType.TYPE)
    public void serialize(Event<Ticker> event) {
        Ticker ticker = event.getData();
        repository.insert(ticker);
    }

    @Transactional
    @Selector(value = SELECTOR_TICKER_TYPE, type = SelectorType.TYPE)
    public void process(Event<Ticker> event) {
        Ticker ticker = event.getData();
        String symbol = ticker.getSymbol();

        Queue<Ticker> timeFrames = framesBySymbol.computeIfAbsent(symbol, key -> new LinkedList<>());

        timeFrames.add(ticker);

        if (TICKER_SIZE == timeFrames.size()) {
            log.debug("symbol: {}", symbol);
            eventBus.notify(SELECTOR_AVERAGE, Event.wrap(new LinkedList<>(timeFrames)));
            timeFrames.poll();
        }
    }

    @Transactional
    @Selector(value = SELECTOR_AVERAGE, type = SelectorType.REGEX)
    public void avg(Event<Queue<Ticker>> event) {
        Queue<Ticker> tickers = event.getData();

        MovingAverage movingAverage = movingAverageService.movingAverage(tickers);
        log.debug("movingAverage: {}", movingAverage);
    }
}