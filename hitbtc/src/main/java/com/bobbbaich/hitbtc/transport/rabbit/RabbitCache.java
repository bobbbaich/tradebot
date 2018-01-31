package com.bobbbaich.hitbtc.transport.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitCache {
    private static final String QUEUE_PATTERN = "queue.%s.%s";
    private static final String EXCHANGE_PATTERN = "exchange.%s";

    private final AmqpAdmin amqpAdmin;

    private Map<String, Queue> queues = new ConcurrentHashMap<>();
    private Map<Class, Exchange> exchanges = new ConcurrentHashMap<>();

    public <T> Queue getQueue(String method, Class<T> clazz) {
        String queueName = getQueueName(clazz, method);
        if (queues.containsKey(queueName)) {
            return queues.get(queueName);
        }

        return createQueue(queueName, clazz);
    }

    public <T> Exchange getExchange(Class<T> clazz) {
        if (exchanges.containsKey(clazz)) {
            return exchanges.get(clazz);
        }

        return createExchange(clazz);
    }

    private <T> Queue createQueue(String queueName, Class<T> clazz) {
        Queue queue = QueueBuilder
                .nonDurable(queueName).build();
        queues.put(queueName, queue);
        amqpAdmin.declareQueue(queue);

        Exchange exchange = getExchange(clazz);
        bind(queue, exchange);

        return queue;
    }

    private <T> Exchange createExchange(Class<T> clazz) {
        String exchangeName = getExchangeName(clazz);
        Exchange exchange = ExchangeBuilder
                .topicExchange(exchangeName).build();
        amqpAdmin.declareExchange(exchange);
        exchanges.put(clazz, exchange);

        return exchange;
    }

    private void bind(Queue queue, Exchange exchange) {
        Binding binding = BindingBuilder
                .bind(queue).to(exchange).with(queue.getName()).noargs();
        amqpAdmin.declareBinding(binding);
    }

    public <T> String getQueueName(Class<T> clazz, String method) {
        return String.format(QUEUE_PATTERN, clazz.getSimpleName(), method);
    }

    public <T> String getExchangeName(Class<T> clazz) {
        return String.format(EXCHANGE_PATTERN, clazz.getSimpleName());
    }
}