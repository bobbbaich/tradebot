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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.ANALYZE_TICKER;
import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.SELECTOR_TICKER_TYPE;

@Slf4j
@Consumer
@RequiredArgsConstructor
public class TickerProcessor {
    private static final int TICKER_SIZE = 10;

    private final EventBus eventBus;
    private final TickerRepository repository;

    //  Ticker map where key is s symbol
    private Map<String, Set<Ticker>> tickerCache = new ConcurrentHashMap<>();

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

        Set<Ticker> tickers = tickerCache.computeIfAbsent(symbol, key -> new HashSet<>(TICKER_SIZE));
        tickers.add(ticker);

        if (TICKER_SIZE == tickers.size()) {
            log.debug("symbol: {}", symbol);
            eventBus.notify(ANALYZE_TICKER, Event.wrap(new HashSet<>(tickers)));
            tickers.clear();
        }
    }

    @Selector(value = ANALYZE_TICKER, type = SelectorType.REGEX)
    public void simpleAvg(Event<Set<Ticker>> event) {
        Set<Ticker> data = event.getData();
        Double averageOpen = data.parallelStream()
                .mapToDouble(Ticker::getOpen)
                .average()
                .getAsDouble();
        log.debug("averageOpen: {}", averageOpen);
    }
}