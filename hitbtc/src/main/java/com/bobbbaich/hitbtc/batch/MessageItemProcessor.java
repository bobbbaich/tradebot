package com.bobbbaich.hitbtc.batch;

import com.bobbbaich.hitbtc.model.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageItemProcessor implements ItemProcessor<Ticker, String> {
    @Override
    public String process(Ticker ticker) throws Exception {
        log.debug("process item: {}", ticker);
        return null;
    }
}
