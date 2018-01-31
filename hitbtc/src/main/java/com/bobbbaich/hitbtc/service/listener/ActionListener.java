package com.bobbbaich.hitbtc.service.listener;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActionListener {
    public static final String SNAPSHOT_CANDLES = "${queue.snapshotCandles}";
    public static final String UPDATE_CANDLES = "${queue.updateCandles}";
    public static final String TICKER = "${queue.ticker}";

    @RabbitListener(queues = TICKER)
    public void processTicker(Ticker ticker) {
        log.debug("{}", ticker);
    }

    @RabbitListener(queues = SNAPSHOT_CANDLES)
    public void snapshotCandles(Candle candle) {
        log.debug("{}", candle);
    }

    @RabbitListener(queues = UPDATE_CANDLES)
    public void updateCandles(Candle candle) {
        log.debug("{}", candle);
    }
}

