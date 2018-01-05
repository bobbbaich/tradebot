package com.bobbbaich.hitbtc.service.market.impl;

import com.bobbbaich.hitbtc.service.market.NotificationService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.client.JsonRpcClient;
import org.kurento.jsonrpc.message.Request;
import org.kurento.jsonrpc.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class HitBtcNotificationService implements NotificationService {
    private JsonRpcClient client;

    @Override
    public Response<JsonElement> subscribeTicker(String symbol) throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method("subscribeTicker")
                .withParam("symbol", symbol)
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> unsubscribeTicker(String symbol) throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method("unsubscribeTicker")
                .withParam("symbol", symbol)
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> subscribeCandles(String symbol, String period) throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method("subscribeCandles")
                .withParam("symbol", symbol)
                .withParam("period", period)
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> unsubscribeCandles(String symbol, String period) throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method("unsubscribeCandles")
                .withParam("symbol", symbol)
                .withParam("period", period)
                .build();
        return client.sendRequest(request);
    }

    @Autowired
    @Qualifier("hitBtcClient")
    public void setClient(JsonRpcClient client) {
        this.client = client;
    }
}
