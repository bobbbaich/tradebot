package com.bobbbaich.hitbtc.service.reactor;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.repository.CandleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;
import reactor.spring.context.annotation.SelectorType;

import java.util.LinkedList;
import java.util.Queue;

import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.SELECTOR_CANDLE_TYPE;

@Slf4j
@Consumer
@RequiredArgsConstructor
public class CandleProcessor {
    private final EventBus eventBus;
    private final CandleRepository repository;

    @Transactional
    @Selector(value = SELECTOR_CANDLE_TYPE, type = SelectorType.TYPE)
    public void serialize(Event<Candle> event) {
        Candle candle = event.getData();
        repository.insert(candle);
    }

    @Transactional
    @Selector(value = SELECTOR_CANDLE_TYPE, type = SelectorType.TYPE)
    public void process(Event<Candle> event) {
    }
}
