package com.bobbbaich.hitbtc.config.batch;

public class BatchConstants {
    //    Step Params
    public static final int CHUNK_SIZE = 200;
    //    Job Params
    public static final String JOB_AMQP_CONSUME_CANDLE = "jobConsumeCandleAndSerialize";
    public static final String JOB_AMQP_CONSUME_TICKER = "jobConsumeTickerAndSerialize";
}
