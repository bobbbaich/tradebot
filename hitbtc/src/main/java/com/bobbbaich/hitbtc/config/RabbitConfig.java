package com.bobbbaich.hitbtc.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableRabbit
@EnableScheduling
public class RabbitConfig {
    private static final String DEFAULT_QUEUE = "queue.default";
    private static final String DEFAULT_EXCHANGE = "exchange.default";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        rabbitTemplate.setQueue(DEFAULT_QUEUE);
        rabbitTemplate.setExchange(DEFAULT_EXCHANGE);

        return rabbitTemplate;
    }

    @Bean
    public Queue defaultQueue() {
        return new Queue(DEFAULT_QUEUE, false);
    }

    @Bean
    public TopicExchange defaultExchange() {
        return new TopicExchange(DEFAULT_EXCHANGE);
    }

    @Bean
    public Binding defaultBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEFAULT_QUEUE);
    }
}
