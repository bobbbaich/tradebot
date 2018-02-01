package com.bobbbaich.hitbtc.transport.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageProducer {
    private RabbitTemplate template;

    public <T> void send(T data, String symbol, String queueName) {
        template.convertAndSend(queueName, data);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate template) {
        this.template = template;
    }
}