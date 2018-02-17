package com.bobbbaich.hitbtc.service.listener;

import com.bobbbaich.hitbtc.model.MovingAverage;
import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.repository.TickerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Slf4j
@Component
@RequiredArgsConstructor
public class TickerListener {
    private final EventBus eventBus;
    private final TickerRepository repository;

    @RabbitListener(queues = {"${queue.ticker}"})
    public void ticker(Ticker ticker) {
        if (ticker != null) {
            MovingAverage mAvg = MovingAverage.builder()
                    .id(ObjectId.get().toString())
                    .timestamp(ticker.getTimestamp())
                    .build();
            ticker.setMovingAverage(mAvg);
            repository.insert(ticker);

            eventBus.notify(Ticker.class, Event.wrap(ticker));
        }
    }
}