package com.bobbbaich.hitbtc.transport;

import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import com.google.gson.Gson;
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

@Slf4j
@Service
public class HitBtcNotificationHandler extends TypeDefaultJsonRpcHandler {
    private static final String RPC_METHOD_PARAM_DATA = "data";

    private Gson gson;
    private CustomMessageSender messageSender;

    @JsonRpcMethod
    public void snapshotCandles(@Named Session session, @Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        JsonObject params = data.getParams();
        JsonArray jsonCandles = params.get(RPC_METHOD_PARAM_DATA).getAsJsonArray();

        for (JsonElement jsonCandle : jsonCandles) {
            sendItem(jsonCandle, Candle.class);
        }
    }

    @JsonRpcMethod
    public void updateCandles(@Named Session session, @Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        sendItem(data.getParams(), Candle.class);
    }

    @JsonRpcMethod
    public void ticker(@Named Session session, @Named(RPC_METHOD_PARAM_DATA) Request<JsonObject> data) {
        sendItem(data.getParams(), Ticker.class);
    }

    private <T> void sendItem(JsonElement json, Class<T> clazz) {
        T item = gson.fromJson(json, clazz);
        messageSender.send(item);
    }

    @Autowired
    public void setMessageSender(CustomMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    //        JsonObject params = data.getParams();
//        JsonArray data1 = params.get(RPC_METHOD_PARAM_DATA).getAsJsonArray();
//
//
//        try {
//
//            FileWriter fw = new FileWriter("open.txt", true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            StringBuffer stringBuffer = new StringBuffer();
//            for (JsonElement jsonElement : data1) {
//                String open = jsonElement.getAsJsonObject().get("open").getAsString();
//                stringBuffer.append(open);
//                stringBuffer.append("\n");
//            }
//            bw.write(stringBuffer.toString());
//            //Closing BufferedWriter Stream
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}
