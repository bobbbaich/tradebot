package com.bobbbaich.hitbtc;

import com.bobbbaich.hitbtc.service.market.MarketService;
import com.bobbbaich.hitbtc.service.market.NotificationMarketService;
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
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class);
        MarketService marketService = run.getBean(MarketService.class);
        NotificationMarketService notificationMarketService = run.getBean(NotificationMarketService.class);


        Response<JsonElement> subscribeTicker = marketService.subscribeTicker("ETHBTC");
        log.debug("{}", subscribeTicker.getResult());

//        JsonElement tickers = notificationMarketService.ticker();
//        log.debug("{}", tickers);

        Response<JsonElement> unsubscribeTicker = marketService.unsubscribeTicker("ETHBTC");
        log.debug("{}", unsubscribeTicker.getResult());
    }
}
