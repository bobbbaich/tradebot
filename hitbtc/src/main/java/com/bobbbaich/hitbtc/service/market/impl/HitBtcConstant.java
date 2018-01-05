package com.bobbbaich.hitbtc.service.market.impl;

public class HitBtcConstant {
    //  Request Params
    static final String PARAM_SYMBOL = "symbol";
    static final String PARAM_PERIOD = "period";
    static final String PARAM_CURRENCY = "currency";
    //  Notification Methods
    static final String METHOD_SUBSCRIBE_TICKER = "subscribeTicker";
    static final String METHOD_UNSUBSCRIBE_TICKER = "unsubscribeTicker";
    static final String METHOD_SUBSCRIBE_CANDLES = "subscribeCandles";
    static final String METHOD_UNSUBSCRIBE_CANDLES = "unsubscribeCandles";
    //  Market Data Methods
    static final String METHOD_GET_CURRENCY = "getCurrency";
    static final String METHOD_GET_CURRENCIES = "getCurrencies";
    static final String METHOD_GET_SYMBOL = "getSymbol";
    static final String METHOD_GET_SYMBOLS = "getSymbols";
}
