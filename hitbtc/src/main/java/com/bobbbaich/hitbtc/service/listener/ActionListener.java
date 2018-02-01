package com.bobbbaich.hitbtc.service.listener;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ActionListener {

    @PostConstruct
    public void postConstruct(){
        log.debug("ActionListener");
    }
    @RabbitListener(queues = {"${queue.ticker}"})
    public void processTicker(Ticker ticker) {
        log.debug("{}", ticker);
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

