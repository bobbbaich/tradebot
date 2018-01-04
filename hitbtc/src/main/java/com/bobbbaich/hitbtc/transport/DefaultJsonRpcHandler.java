package com.bobbbaich.hitbtc.transport;

import com.google.gson.JsonObject;
import org.kurento.jsonrpc.JsonRpcMethod;
import org.kurento.jsonrpc.Session;
import org.kurento.jsonrpc.TypeDefaultJsonRpcHandler;
import org.kurento.jsonrpc.message.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Named;

@Service
public class DefaultJsonRpcHandler extends TypeDefaultJsonRpcHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultJsonRpcHandler.class);

    @JsonRpcMethod
    public void snapshotCandles(@Named Session session, @Named("data") Request<JsonObject> params) {
        System.out.println("snapshotCandles");
        System.out.println("params: " + params.getParams());
    }

    @JsonRpcMethod
    public void updateCandles(@Named Session session, @Named("data") Request<JsonObject> params) {
        System.out.println("updateCandles");
        System.out.println("params: " + params.getParams());
    }
}
