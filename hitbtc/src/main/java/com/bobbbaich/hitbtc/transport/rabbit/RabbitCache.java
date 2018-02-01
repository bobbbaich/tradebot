package com.bobbbaich.hitbtc.transport.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitCache {
    private static final String EXCHANGE_PATTERN = "exchange.%s";
    private final AmqpAdmin amqpAdmin;

    private Map<String, Queue> queues = new ConcurrentHashMap<>();
    private Map<String, Exchange> exchanges = new ConcurrentHashMap<>();

    @Value("${queue.snapshotCandles}")
    public String SNAPSHOT_CANDLES;
    @Value("${queue.updateCandles}")
    public String UPDATE_CANDLES;
    @Value("${queue.ticker}")
    public String TICKER;


    @Bean
    public Queue snapshotQueue() {
        Queue queue = new Queue(SNAPSHOT_CANDLES, false);
        queues.put(SNAPSHOT_CANDLES, queue);
        return queue;
    }

    @Bean
    public Queue updateQueue() {
        Queue queue = new Queue(UPDATE_CANDLES, false);
        queues.put(UPDATE_CANDLES, queue);
        return queue;
    }

    @Bean
    public Queue tickerQueue() {
        Queue queue = new Queue(TICKER, false);
        queues.put(TICKER, queue);
        return queue;
    }

    public Queue getQueue(String queueName) {
        if (queues.containsKey(queueName)) {
            return queues.get(queueName);
        }

        return createQueue(queueName);
    }

    public Exchange getExchange(String queueName) {
        String exchangeName = getExchangeNameByQueueName(queueName);
        if (exchanges.containsKey(exchangeName)) {
            return exchanges.get(exchangeName);
        }

        return createExchange(exchangeName);
    }

    private Queue createQueue(String queueName) {
        Queue queue = QueueBuilder
                .nonDurable(queueName).build();
        Exchange exchange = getExchange(queueName);
        queues.put(queueName, queue);
        amqpAdmin.declareQueue(queue);
        bind(queue, exchange);
        return queue;
    }

    private Exchange createExchange(String exchangeName) {
        Exchange exchange = ExchangeBuilder
                .topicExchange(exchangeName).build();
        amqpAdmin.declareExchange(exchange);
        exchanges.put(exchangeName, exchange);

        return exchange;
    }

    private void bind(Queue queue, Exchange exchange) {
        Binding binding = BindingBuilder
                .bind(queue).to(exchange).with(queue.getName()).noargs();
        amqpAdmin.declareBinding(binding);
    }

    public String getExchangeNameByQueueName(String queueName) {
        return String.format(EXCHANGE_PATTERN, queueName);
    }

}