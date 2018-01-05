package com.bobbbaich.hitbtc.service.market.impl;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.message.Request;

@Slf4j
public class RequestBuilder {
    private Request<JsonObject> request;
    private JsonObject params;

    public RequestBuilder() {
        request = new Request<>();
        params = new JsonObject();
    }

    public RequestBuilder method(String method) {
        request.setMethod(method);
        return this;
    }

    public RequestBuilder withParam(String key, String value) {
        params.addProperty(key, value);
        return this;
    }

    public Request<JsonObject> build() {
        request.setParams(params);
        return request;
    }
}
