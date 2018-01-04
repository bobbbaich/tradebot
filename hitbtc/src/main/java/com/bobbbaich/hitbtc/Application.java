package com.bobbbaich.hitbtc;

import com.bobbbaich.hitbtc.service.market.MarketService;
import com.bobbbaich.hitbtc.service.market.NotificationService;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.message.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class);
        NotificationService ns = run.getBean(NotificationService.class);

        Response<JsonElement> subscribeCandles = ns.subscribeCandles("ETHBTC");
        System.out.println("Thread.sleep(10000);");
        Thread.sleep(10000);
        Response<JsonElement> unsubscribeCandles = ns.unsubscribeCandles("ETHBTC");
        System.out.println("Thread.sleep(20000);");

        Thread.sleep(20000);

        Response<JsonElement> subscribeCandles2 = ns.subscribeCandles("ETHUSD");
        System.out.println("Thread.sleep(10000);");
        Thread.sleep(10000);
        Response<JsonElement> unsubscribeCandles2 = ns.unsubscribeCandles("ETHUSD");

    }
}
