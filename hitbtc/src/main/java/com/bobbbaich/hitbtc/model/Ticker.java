package com.bobbbaich.hitbtc.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "tickers")
public class Ticker {
    private Double ask;//: 0.054464,
    private Double bid;//: 0.054463,
    private Double last;//: 0.054463,
    private Double open;//: 0.057133,
    private Double low;//: 0.053615,
    private Double high;//: 0.057559,
    private Double volume;//: 33068.346,
    private Double volumeQuote;//: 1832.687530809,
    private Date timestamp;//: 2017-10-19T15:45:44.941Z,
    private String symbol;//: ETHBTC

    private MovingAverage movingAverage;
}
