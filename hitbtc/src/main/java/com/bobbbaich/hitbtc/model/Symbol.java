package com.bobbbaich.hitbtc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "symbols")
public class Symbol {
    @Id
    private String id;
    private String symbol;
    private String baseCurrency;
    private String quoteCurrency;
    private String quantityIncrement;
    private String tickSize; //A tick size is the minimum price movement of a trading instrument.
    // Below are not required
    private String takeLiquidityRate;
    private String provideLiquidityRate;
    private String feeCurrency;
}