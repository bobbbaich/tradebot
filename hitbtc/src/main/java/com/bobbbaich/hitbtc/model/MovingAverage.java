package com.bobbbaich.hitbtc.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "moving_averages")
public class MovingAverage {
    private Integer period;
    private Double simple;
    private Double weighted;
    private Double exponential;
    private Date timestamp;
    private MovingAverage previous;
}