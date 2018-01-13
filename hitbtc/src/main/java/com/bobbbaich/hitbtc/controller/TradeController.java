package com.bobbbaich.hitbtc.controller;

import com.bobbbaich.hitbtc.model.Symbol;
import com.bobbbaich.hitbtc.service.market.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/trade")
public class TradeController {

    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> analyse(@RequestBody Symbol symbol) throws IOException {
//        notificationService.subscribeCandles(symbol.getId(), "M30");
        notificationService.subscribeTicker(symbol.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}