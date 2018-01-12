package com.bobbbaich.hitbtc.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomMessageSender {

    private RabbitTemplate template;

    private Queue queue;

    public <T> void send(T data) {
        this.template.convertAndSend(queue.getName(), data);
    }

    public <T> void send(T item, Class<T> clazz) {
        send(item);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}
