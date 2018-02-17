package com.bobbbaich.hitbtc.service;

import com.bobbbaich.hitbtc.service.market.MarketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublicServiceTest {
    @Autowired
    private MarketService service;

    @Test
    public void symbols() throws Exception {
        service.getSymbols();
    }

}