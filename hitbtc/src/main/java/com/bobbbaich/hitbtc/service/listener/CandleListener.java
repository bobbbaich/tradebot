package com.bobbbaich.hitbtc.service.listener;

import com.bobbbaich.hitbtc.model.Candle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

import static com.bobbbaich.hitbtc.service.reactor.SelectorKey.SELECTOR_CANDLE_TYPE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CandleListener {
    private final EventBus eventBus;

    @RabbitListener(queues = "${queue.snapshotCandles}")
    public void snapshotCandle(Candle candle) {
        if (candle != null) {
            eventBus.notify(SELECTOR_CANDLE_TYPE, Event.wrap(candle));
        }
    }

    @RabbitListener(queues = "${queue.updateCandles}")
    public void updateCandles(Candle candle) {
        if (candle != null) {
            eventBus.notify(SELECTOR_CANDLE_TYPE, Event.wrap(candle));
        }
    }
}