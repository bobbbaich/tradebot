package com.bobbbaich.hitbtc.service.market;

import com.google.gson.JsonElement;

import java.io.IOException;

public interface NotificationMarketService {
    JsonElement ticker() throws IOException;
}
