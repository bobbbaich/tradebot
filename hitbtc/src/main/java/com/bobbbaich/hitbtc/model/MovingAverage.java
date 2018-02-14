package com.bobbbaich.hitbtc.model;

import lombok.Builder;

import java.util.Date;

@Builder
public class MovingAverage {
    private Integer period;
    private Double simple;
    private Double weighted;
    private Double exponential;
    private Date timestamp;

    @Override
    public String toString() {
        return "MovingAverage{" +
                "period=" + period +
                ", simple=" + simple +
                ", weighted=" + weighted +
                ", exponential=" + exponential +
                ", timestamp=" + timestamp +
                '}';
    }
}