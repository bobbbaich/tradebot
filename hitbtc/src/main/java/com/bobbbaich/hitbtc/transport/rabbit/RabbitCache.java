package com.bobbbaich.hitbtc.transport.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RabbitCache {
    private static final String QUEUE_PATTERN = "queue.%s.%s";
    private static final String EXCHANGE_PATTERN = "exchange.%s";

    private Map<String, Queue> queues = new ConcurrentHashMap<>();
    private Map<String, Exchange> exchanges = new ConcurrentHashMap<>();

    private AmqpAdmin amqpAdmin;

    public Set<String> getQueuesNames() {
        return queues.keySet();
    }

    public Set<Queue> getQueues() {
        return new HashSet<>(queues.values());
    }

    public Set<Exchange> getExchanges() {
        return new HashSet<>(exchanges.values());
    }

    public Queue getQueue(String symbol, String method) {
        String queueName = String.format(QUEUE_PATTERN, symbol, method);
        if (queues.containsKey(queueName)) {
            return queues.get(queueName);
        }

        return createQueue(symbol, queueName);
    }

    private Queue createQueue(String symbol, String queueName) {
        Queue queue = QueueBuilder
                .nonDurable(queueName).build();
        queues.put(queueName, queue);
        amqpAdmin.declareQueue(queue);
        bind(symbol, queue);

        return queue;
    }

    Exchange getExchange(String symbol) {
        String exchangeName = String.format(EXCHANGE_PATTERN, symbol);
        if (exchanges.containsKey(exchangeName)) {
            return exchanges.get(exchangeName);
        }

        return createExchange(symbol, exchangeName);
    }

    private Exchange createExchange(String symbol, String exchangeName) {
        Exchange exchange = ExchangeBuilder
                .topicExchange(exchangeName).build();
        amqpAdmin.declareExchange(exchange);
        exchanges.put(symbol, exchange);

        return exchange;
    }

    private void bind(String symbol, Queue queue) {
        Exchange exchange = getExchange(symbol);
        Binding binding = BindingBuilder
                .bind(queue).to(exchange).with(queue.getName()).noargs();
        amqpAdmin.declareBinding(binding);
    }

    @Autowired
    public void setRabbitAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }
}
