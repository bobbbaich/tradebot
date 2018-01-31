package com.bobbbaich.hitbtc.transport.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MessageProducer {
    private RabbitCache cache;
    private RabbitTemplate template;
    private Map<String, CorrelationData> correlationData = new HashMap<>();

    public <T> void send(T data, String symbol, String method, Class<T> clazz) {
        Queue queue = cache.getQueue(method, clazz);
        Exchange exchange = cache.getExchange(clazz);

        CorrelationData correlationData = getCorrelationData(symbol);
        template.convertAndSend(exchange.getName(), queue.getName(), data, correlationData);
    }

    private CorrelationData getCorrelationData(String symbol) {
        if (correlationData.containsKey(symbol)) {
            return correlationData.get(symbol);
        }
        CorrelationData correlationData = new CorrelationData(symbol);
        this.correlationData.put(symbol, correlationData);

        return correlationData;
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