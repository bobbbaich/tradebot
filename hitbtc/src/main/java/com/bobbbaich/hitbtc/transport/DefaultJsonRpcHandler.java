package com.bobbbaich.hitbtc.transport;

import org.kurento.jsonrpc.JsonRpcMethod;
import org.kurento.jsonrpc.TypeDefaultJsonRpcHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultJsonRpcHandler extends TypeDefaultJsonRpcHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultJsonRpcHandler.class);

    @JsonRpcMethod
    public void ticker() {
        log.debug("ticker");
    }
}
