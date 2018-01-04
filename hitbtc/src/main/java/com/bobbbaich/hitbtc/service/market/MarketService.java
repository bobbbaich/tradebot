package com.bobbbaich.hitbtc.service.market;

import com.google.gson.JsonElement;
import org.kurento.jsonrpc.message.Response;

import java.io.IOException;

public interface MarketService {
    Response<JsonElement> getCurrency(String currencу) throws IOException;

    Response<JsonElement> getCurrencies() throws IOException;

    Response<JsonElement> getSymbol(String symbol) throws IOException;

    Response<JsonElement> getSymbols() throws IOException;
}
