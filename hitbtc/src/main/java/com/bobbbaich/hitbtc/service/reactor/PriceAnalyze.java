package com.bobbbaich.hitbtc.service.reactor;

import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.repository.TickerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;
import reactor.spring.context.annotation.SelectorType;

import java.util.HashSet;
import java.util.Set;

import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.*;

@Slf4j
@Consumer
@RequiredArgsConstructor
public class PriceAnalyze {
    private static final int TICKER_SIZE = 50;

    private final EventBus eventBus;
    private final TickerRepository repository;

    private Set<Ticker> tickers = new HashSet<>(TICKER_SIZE);

    @Transactional
    @Selector(value = SELECTOR_TICKER_TYPE, type = SelectorType.TYPE)
    public void serialize(Event<Ticker> event) {
        Ticker ticker = event.getData();
        repository.insert(ticker);
    }

    @Transactional
    @Selector(value = SELECTOR_CANDLE_TYPE, type = SelectorType.TYPE)
    public void process(Event<Ticker> event) {
        tickers.add(event.getData());

        if (TICKER_SIZE == tickers.size()) {
            eventBus.send(ANALYZE_TICKER, Event.wrap(tickers));
            tickers.clear();
        }
    }
}