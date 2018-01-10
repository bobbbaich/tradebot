package com.bobbbaich.hitbtc.transport;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.message.Request;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomMessageSender {

    private RabbitTemplate template;

    private Queue queue;

    public void send(Request<JsonObject> data) {
        this.template.convertAndSend(queue.getName(), data);
        log.debug("Message Sent: {}", data.getParams());
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
