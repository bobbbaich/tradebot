package com.bobbbaich.hitbtc.config.batch;

import com.bobbbaich.hitbtc.batch.MessageProcessor;
import com.bobbbaich.hitbtc.batch.MessageStreamReader;
import com.bobbbaich.hitbtc.model.Candle;
import com.bobbbaich.hitbtc.model.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.bobbbaich.hitbtc.config.batch.BatchConstants.CHUNK_SIZE;

@Slf4j
@Configuration
public class StepConfig {
    private final StepBuilderFactory steps;

    @Autowired
    public StepConfig(StepBuilderFactory steps) {
        this.steps = steps;
    }

    @Bean("consumeCandle")
    protected Step consumeCandle(MessageStreamReader<Candle> reader, MessageProcessor<Candle, Candle> processor, MongoItemWriter<Candle> itemWriter) {
        return steps.get("consumeCandle")
                .<Candle, Candle>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(itemWriter)
                .build();
    }

    @Bean("consumeTicker")
    protected Step consumeTicker(MessageStreamReader<Ticker> reader, MessageProcessor<Ticker, Ticker> processor, MongoItemWriter<Ticker> itemWriter) {
        return steps.get("consumeTicker")
                .<Ticker, Ticker>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(itemWriter)
                .build();
    }
}
