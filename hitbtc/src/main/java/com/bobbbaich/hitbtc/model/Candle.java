package com.bobbbaich.hitbtc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "candles")
public class Candle {
    @Id
    private String id;
    private Double open;//: 0.054801,
    private Double close;//: 0.054625,
    private Double min;//: 0.054601,
    private Double max;//: 0.054894,
    private Double volume;//: 380.750,
    private Double volumeQuote;//: 20.844237223
    private Date timestamp;//: 2017-10-19T15:00:00.000Z,
    private String symbol;
}