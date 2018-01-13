package com.bobbbaich.hitbtc.transport;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.transport.rabbit.MessageProducer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.JsonRpcMethod;
import org.kurento.jsonrpc.Session;
import org.kurento.jsonrpc.TypeDefaultJsonRpcHandler;
import org.kurento.jsonrpc.message.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Named;

@Slf4j
@Service
public class HitBtcNotificationHandler extends TypeDefaultJsonRpcHandler {
    private static final String RPC_METHOD_PARAM_DATA = "data";
    private static final String RPC_METHOD_PARAM_SYMBOL = "symbol";

    private Gson gson;
    private MessageProducer messageSender;

    @JsonRpcMethod
    public void snapshotCandles(@Named Session session, @Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        sendItem(data, Candle.class);
    }

    @JsonRpcMethod
    public void updateCandles(@Named Session session, @Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        sendItem(data, Candle.class);
    }

    @JsonRpcMethod
    public void ticker(@Named Session session, @Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        sendItem(data, Ticker.class);
    }

    private <T> void sendItem(Request<JsonObject> request, Class<T> clazz) {
        Assert.notNull(request, "Param 'request' cannot be null!");
        JsonObject params = request.getParams();

        T item = gson.fromJson(params, clazz);
        String symbol = params.get(RPC_METHOD_PARAM_SYMBOL).getAsString();

        messageSender.sendTo(item, symbol, request.getMethod());
    }

    @Autowired
    public void setMessageSender(MessageProducer messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }
}