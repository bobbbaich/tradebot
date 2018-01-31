package com.bobbbaich.hitbtc.transport.rabbit.listener;

import com.bobbbaich.hitbtc.model.Candle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TickerListener {
    @RabbitListener(queues = "queue.Candle.snapshotCandles")
    public void processCandle(Candle candle) {
        log.warn("{}", candle);
    }

    @RabbitListener(queues = "queue.Candle.updateCandles")
    public void processCandlae(Candle candle) {
        log.warn("{}", candle);
    }
}
