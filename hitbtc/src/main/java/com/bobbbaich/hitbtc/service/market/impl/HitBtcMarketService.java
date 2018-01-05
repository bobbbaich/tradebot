package com.bobbbaich.hitbtc.service.market.impl;

import com.bobbbaich.hitbtc.service.market.MarketService;
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

import static com.bobbbaich.hitbtc.service.market.impl.HitBtcConstant.*;

@Slf4j
@Service
public class HitBtcMarketService implements MarketService {
    private JsonRpcClient client;

    @Override
    public Response<JsonElement> getCurrency(String currency) throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method(METHOD_GET_CURRENCY)
                .withParam(PARAM_CURRENCY, currency)
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> getCurrencies() throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method(METHOD_GET_CURRENCIES)
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> getSymbol(String symbol) throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method(METHOD_GET_SYMBOL)
                .withParam(PARAM_SYMBOL, symbol)
                .build();
        return client.sendRequest(request);
    }

    @Override
    public Response<JsonElement> getSymbols() throws IOException {
        Request<JsonObject> request = new RequestBuilder()
                .method(METHOD_GET_SYMBOLS)
                .build();
        return client.sendRequest(request);
    }

    @Autowired
    @Qualifier("hitBtcClient")
    public void setClient(JsonRpcClient client) {
        this.client = client;
    }
}