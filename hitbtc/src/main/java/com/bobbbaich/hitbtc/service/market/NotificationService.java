package com.bobbbaich.hitbtc.service.market;

import com.google.gson.JsonElement;
import org.kurento.jsonrpc.message.Response;

import java.io.IOException;

public interface NotificationService {
    Response<JsonElement> subscribeTicker(String symbol) throws IOException;

    Response<JsonElement> unsubscribeTicker(String symbol) throws IOException;

    Response<JsonElement> subscribeCandles(String symbol, String period) throws IOException;

    Response<JsonElement> unsubscribeCandles(String symbol, String period) throws IOException;
}
