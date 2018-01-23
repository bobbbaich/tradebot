package com.bobbbaich.hitbtc.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProcessor<I, O> implements ItemProcessor<I, O> {

    @Override
    public O process(I item) throws Exception {
        log.debug("received item: {}", item);
        return (O) item;
    }
}
