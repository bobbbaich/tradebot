package com.bobbbaich.hitbtc.batch;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.message.Request;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageItemProcessor implements ItemProcessor<Request<JsonObject>, String> {
    @Override
    public String process(Request<JsonObject> item) throws Exception {
        log.debug("process item: {}", item.getParams());
        return null;
    }
}
