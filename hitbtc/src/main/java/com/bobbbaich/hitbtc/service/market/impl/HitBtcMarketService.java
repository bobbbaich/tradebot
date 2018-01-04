package com.bobbbaich.hitbtc.service.market.impl;

import com.bobbbaich.hitbtc.service.market.MarketService;
import com.bobbbaich.hitbtc.service.market.RequestBuilder;
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
public class HitBtcMarketService implements MarketService {
    private JsonRpcClient client;

    @Override
    public Response<JsonElement> getCurrency(String currency) throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method("getCurrency")
                .withParam("currency", currency)
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> getCurrencies() throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method("getCurrencies")
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> getSymbol(String symbol) throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method("getSymbol")
                .withParam("symbol", symbol)
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> getSymbols() throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method("getSymbols")
                .build();
        return client.sendRequest(request);
    }

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

    @Autowired
    @Qualifier("hitBtcClient")
    public void setClient(JsonRpcClient client) {
        this.client = client;
    }
}