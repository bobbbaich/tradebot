package com.bobbbaich.hitbtc.service.reactor;

import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.repository.TickerRepository;
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

import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.SELECTOR_AVERAGE;
import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.SELECTOR_TICKER_TYPE;

@Slf4j
@Consumer
@RequiredArgsConstructor
public class TickerProcessor {
    public static final int TICKER_SIZE = 84;

    private final EventBus eventBus;
    private final TickerRepository repository;

    //  Ticker map where key is s symbol
    private Map<String, Queue<Ticker>> tickersBySymbol = new ConcurrentHashMap<>();

//    @Transactional
//    @Selector(value = SELECTOR_TICKER_TYPE, type = SelectorType.TYPE)
//    public void serialize(Event<Ticker> event) {
//        Ticker ticker = event.getData();
//        repository.insert(ticker);
//    }

    @Transactional
    @Selector(value = SELECTOR_TICKER_TYPE, type = SelectorType.TYPE)
    public void analyze(Event<Ticker> event) {
        Ticker ticker = event.getData();

        Queue<Ticker> tickers = getLastTickers(ticker);
        if (TICKER_SIZE == tickers.size()) {
            eventBus.notify(SELECTOR_AVERAGE, Event.wrap(new LinkedList<>(tickers)));
        }
    }

    //  Moving Average Price is computing by last ticker price.
    private Queue<Ticker> getLastTickers(Ticker ticker) {
        Assert.notNull(ticker, "Ticker cannot be null!");
        Queue<Ticker> tickers = tickersBySymbol.computeIfAbsent(ticker.getSymbol(), key -> new LinkedList<>());

        if (TICKER_SIZE == tickers.size()) tickers.poll();
        tickers.add(ticker);

        return tickers;
    }
}