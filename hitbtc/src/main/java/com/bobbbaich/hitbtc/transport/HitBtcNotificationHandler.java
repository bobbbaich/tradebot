package com.bobbbaich.hitbtc.transport;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.transport.rabbit.MessageProducer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.JsonRpcMethod;
import org.kurento.jsonrpc.Session;
import org.kurento.jsonrpc.TypeDefaultJsonRpcHandler;
import org.kurento.jsonrpc.message.Request;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Named;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitBtcNotificationHandler extends TypeDefaultJsonRpcHandler {
    private static final String RPC_METHOD_PARAM_DATA = "data";
    private static final String RPC_METHOD_PARAM_SYMBOL = "symbol";

    private final Gson gson;
    private final MessageProducer producer;

    @JsonRpcMethod
    public void snapshotCandles(@Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        sendItem(data, Candle.class);
    }

    @JsonRpcMethod
    public void updateCandles(@Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        sendItem(data, Candle.class);
    }

    @JsonRpcMethod
    public void ticker(@Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        sendItem(data, Ticker.class);
    }

    private <T> void sendItem(Request<JsonObject> request, Class<T> clazz) {
        Assert.notNull(request, "Param 'request' cannot be null!");
        String method = request.getMethod();
        JsonObject params = request.getParams();

        Assert.notNull(request, "Param 'params' cannot be null!");
        String symbol = params.get(RPC_METHOD_PARAM_SYMBOL).getAsString();
        JsonArray items = params.getAsJsonArray(RPC_METHOD_PARAM_DATA);

        if (items != null) {
            for (JsonElement jsonItem : items) {
                T item = gson.fromJson(jsonItem, clazz);
                producer.send(item, symbol, method, clazz);
            }
        } else {
            T item = gson.fromJson(params, clazz);
            producer.send(item, symbol, method, clazz);
        }
    }
}