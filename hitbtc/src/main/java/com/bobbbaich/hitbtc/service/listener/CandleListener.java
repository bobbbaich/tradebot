package com.bobbbaich.hitbtc.service.listener;

import com.bobbbaich.hitbtc.model.Candle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CandleListener {
}
