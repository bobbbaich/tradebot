package com.bobbbaich.hitbtc.service.listener;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionListener {

    private final MongoTemplate mongo;

    @RabbitListener(queues = {"${queue.ticker}"})
    public void processTicker(Ticker ticker) {
        mongo.insert(ticker);
    }

    @RabbitListener(queues = "${queue.snapshotCandles}")
    public void snapshotCandle(Candle candle) {
        log.debug("{}", candle);
    }

    @RabbitListener(queues = "${queue.updateCandles}")
    public void updateCandles(Candle candle) {
        log.debug("{}", candle);
    }
}