package com.bobbbaich.hitbtc;

import com.bobbbaich.hitbtc.service.market.NotificationService;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.message.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class);
        NotificationService ns = run.getBean(NotificationService.class);

//        String period = "M30";
//        Response<JsonElement> subscribeCandles = ns.subscribeCandles("ETHBTC", period);
//        log.debug("Thread.sleep(10000);");
//        Thread.sleep(10000);
//        Response<JsonElement> unsubscribeCandles = ns.unsubscribeCandles("BTCUSDT", period);
//        log.debug("Thread.sleep(20000);");
//
//        Thread.sleep(20000);
//
//        Response<JsonElement> subscribeCandles2 = ns.subscribeCandles("ETHUSD", period);
//        log.debug("Thread.sleep(10000);");
//        Thread.sleep(10000);
//        Response<JsonElement> unsubscribeCandles2 = ns.unsubscribeCandles("ETHUSD", period);

        Response<JsonElement> subscribeTicker = ns.subscribeTicker("ETHBTC");
        Thread.sleep(20000);
        Response<JsonElement> unsubscribeTicker = ns.unsubscribeTicker("ETHBTC");
    }
}
