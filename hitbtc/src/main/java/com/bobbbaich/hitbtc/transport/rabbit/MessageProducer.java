package com.bobbbaich.hitbtc.transport.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageProducer {
    private RabbitCache cache;
    private RabbitTemplate template;

    public <T> void sendTo(T data, String symbol, String method) {
        Queue queue = cache.getQueue(symbol, method);
        Exchange exchange = cache.getExchange(symbol);

        this.template.convertAndSend(exchange.getName(), queue.getName(), data);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setCache(RabbitCache cache) {
        this.cache = cache;
    }
}
