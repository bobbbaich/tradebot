package com.bobbbaich.hitbtc.config;

import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.JsonRpcHandler;
import org.kurento.jsonrpc.client.JsonRpcClient;
import org.kurento.jsonrpc.client.JsonRpcClientNettyWebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Slf4j
@Configuration
public class WebSocketConfig {
    private static final String API_WS_HITBTC = "wss://api.hitbtc.com/api/2/ws";

    @Bean(name = "hitBtcClient")
    public JsonRpcClient hitBtcClient(JsonRpcHandler<?> handler) {
        JsonRpcClient client = new JsonRpcClientNettyWebSocket(API_WS_HITBTC);
        client.setServerRequestHandler(handler);
        return client;
    }

    @Bean(name = "hitBtcAuthorizedClient")
    public JsonRpcClient hitBtcAuthorizedClient(JsonRpcHandler<?> handler) {
        JsonRpcClient client = new JsonRpcClientNettyWebSocket(API_WS_HITBTC);
        client.setServerRequestHandler(handler);
        return client;
    }
}
