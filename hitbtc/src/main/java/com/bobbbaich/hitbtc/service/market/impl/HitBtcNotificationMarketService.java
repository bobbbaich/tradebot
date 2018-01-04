package com.bobbbaich.hitbtc.service.market.impl;

import com.bobbbaich.hitbtc.service.market.NotificationMarketService;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.client.JsonRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class HitBtcNotificationMarketService implements NotificationMarketService {


    private JsonRpcClient client;

    @Override
    public JsonElement ticker() throws IOException {
        return client.sendRequest("ticker");
    }

    @Autowired
    @Qualifier("hitBtcClient")
    public void setClient(JsonRpcClient client) {
        this.client = client;
    }
}
