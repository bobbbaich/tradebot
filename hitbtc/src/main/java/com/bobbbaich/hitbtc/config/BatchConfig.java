package com.bobbbaich.hitbtc.config;

import com.bobbbaich.hitbtc.batch.MessageProcessor;
import com.bobbbaich.hitbtc.batch.MessageStreamReader;
import com.bobbbaich.hitbtc.model.Ticker;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
@EnableBatchProcessing
public class BatchConfig {
    private static final int CHUNK_SIZE = 1;

    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job job(Step step1) {
        return jobs
                .get("amqp_consume")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    protected Step step1(MessageStreamReader<Ticker> reader, MessageProcessor<Ticker, String> processor) {
        return steps.get("step1")
                .<Ticker, String>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .build();
    }
}
