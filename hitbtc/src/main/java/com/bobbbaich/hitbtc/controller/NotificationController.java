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
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping("/candle/subscribe")
    public ResponseEntity<String> subscribeCandles(@RequestBody Symbol symbol) throws IOException {
        notificationService.subscribeCandles(symbol.getId(), "M30");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/candle/unsubscribe")
    public ResponseEntity<String> unsubscribeCandles(@RequestBody Symbol symbol) throws IOException {
        notificationService.unsubscribeCandles(symbol.getId(), "M30");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/ticker/subscribe")
    public ResponseEntity<String> subscribeTicker(@RequestBody Symbol symbol) throws IOException {
        notificationService.subscribeTicker(symbol.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/ticker/unsubscribe")
    public ResponseEntity<String> unsubscribeTicker(@RequestBody Symbol symbol) throws IOException {
        notificationService.unsubscribeTicker(symbol.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}