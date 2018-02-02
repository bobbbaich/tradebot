package com.bobbbaich.hitbtc.service.listener;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionListener {

    private final EventBus eventBus;

    @RabbitListener(queues = {"${queue.ticker}"})
    public void processTicker(Ticker ticker) {
        eventBus.notify(Event.wrap(ticker));
    }

    @RabbitListener(queues = "${queue.snapshotCandles}")
    public void snapshotCandle(Candle candle) {
        eventBus.notify(Event.wrap(candle));
    }

    @RabbitListener(queues = "${queue.updateCandles}")
    public void updateCandles(Candle candle) {
        eventBus.notify(Event.wrap(candle));
    }
}