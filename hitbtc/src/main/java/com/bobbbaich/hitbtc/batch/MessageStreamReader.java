package com.bobbbaich.hitbtc.batch;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@StepScope
public class MessageStreamReader<T> extends AbstractItemStreamItemReader<T> {
    private String queue;
    private final AmqpTemplate amqpTemplate;

    @Value("#{jobParameters[queue]}")
    public void setQueue(String queue) {
        this.queue = queue;
    }

    @Autowired
    public MessageStreamReader(final AmqpTemplate amqpTemplate) {
        Assert.notNull(amqpTemplate, "AmqpTemplate must not be null.");
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Assert.hasLength(queue, "Queue cannot be null or empty!");
        return (T) amqpTemplate.receiveAndConvert(queue);
    }
}
