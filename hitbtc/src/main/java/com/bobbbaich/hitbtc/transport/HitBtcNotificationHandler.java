package com.bobbbaich.hitbtc.transport;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.JsonRpcMethod;
import org.kurento.jsonrpc.Session;
import org.kurento.jsonrpc.TypeDefaultJsonRpcHandler;
import org.kurento.jsonrpc.message.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Named;

@Slf4j
@Service
public class HitBtcNotificationHandler extends TypeDefaultJsonRpcHandler {

    private CustomMessageSender messageSender;

    @JsonRpcMethod
    public synchronized void snapshotCandles(@Named Session session, @Named("data") Request<JsonObject> data) {
        log.debug("snapshotCandles");
        log.debug("data: ", data.getParams());
    }

    @JsonRpcMethod
    public synchronized void updateCandles(@Named Session session, @Named("data") Request<JsonObject> data) {
        log.debug("updateCandles");
        log.debug("data: ", data.getParams());
    }

    @JsonRpcMethod
    public synchronized void ticker(@Named Session session, @Named("data") Request<JsonObject> data) {
        log.debug("@JsonRpcMethod -> 'ticker' - data: {}", data.getParams());
        messageSender.send(data);
    }

    @Autowired
    public void setMessageSender(CustomMessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
