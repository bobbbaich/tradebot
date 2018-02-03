package com.bobbbaich.hitbtc.service.listener;

import com.bobbbaich.hitbtc.model.Ticker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Component
@RequiredArgsConstructor
public class TickerListener {
    private final EventBus eventBus;

    @RabbitListener(queues = {"${queue.ticker}"})
    public void ticker(Ticker ticker) {
        if (ticker != null) {
            eventBus.notify(Ticker.class, Event.wrap(ticker));
        }
    }
}