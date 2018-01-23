package com.bobbbaich.hitbtc.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "candles")
public class Candle {
    private double open;//: 0.054801,
    private double close;//: 0.054625,
    private double min;//: 0.054601,
    private double max;//: 0.054894,
    private double volume;//: 380.750,
    private double volumeQuote;//: 20.844237223
    private Date timestamp;//: 2017-10-19T15:00:00.000Z,
}
