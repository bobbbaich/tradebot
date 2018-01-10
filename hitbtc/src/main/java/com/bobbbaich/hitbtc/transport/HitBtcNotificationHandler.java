package com.bobbbaich.hitbtc.transport;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.JsonRpcMethod;
import org.kurento.jsonrpc.Session;
import org.kurento.jsonrpc.TypeDefaultJsonRpcHandler;
import org.kurento.jsonrpc.message.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Named;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Service
public class HitBtcNotificationHandler extends TypeDefaultJsonRpcHandler {

    private CustomMessageSender messageSender;

    @JsonRpcMethod
    public synchronized void snapshotCandles(@Named Session session, @Named("data") Request<JsonObject> data) {
        log.debug("snapshotCandles");
        log.debug("data: ", data.getParams());
        JsonObject params = data.getParams();
        JsonArray data1 = params.get("data").getAsJsonArray();


        try {

            FileWriter fw = new FileWriter("open.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuffer stringBuffer = new StringBuffer();
            for (JsonElement jsonElement : data1) {
                String open = jsonElement.getAsJsonObject().get("open").getAsString();
                stringBuffer.append(open);
                stringBuffer.append("\n");
            }
            bw.write(stringBuffer.toString());
            //Closing BufferedWriter Stream
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
