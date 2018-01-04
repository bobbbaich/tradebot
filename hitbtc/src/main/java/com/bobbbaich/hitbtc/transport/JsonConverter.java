package com.bobbbaich.hitbtc.transport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter implements Converter<JsonElement> {
    private Gson gson = new GsonBuilder().create();

    @Override
    public <F> JsonElement from(F from, Class<F> type) {
        return gson.toJsonTree(from, type);
    }

    @Override
    public <T> T to(JsonElement to, Class<T> type) {
        return gson.fromJson(to, type);
    }
}