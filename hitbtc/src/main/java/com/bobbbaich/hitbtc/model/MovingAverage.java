package com.bobbbaich.hitbtc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;


@Getter
@Setter
@ToString
@Builder
public class MovingAverage {
    @Id
    private String id;
    private Integer period;
    private Double simple;
    private Double weighted;
    private Double exponential;
    private Date timestamp;
}