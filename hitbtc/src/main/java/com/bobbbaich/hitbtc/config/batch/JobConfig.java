package com.bobbbaich.hitbtc.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.bobbbaich.hitbtc.config.batch.BatchConstants.JOB_AMQP_CONSUME_CANDLE;
import static com.bobbbaich.hitbtc.config.batch.BatchConstants.JOB_AMQP_CONSUME_TICKER;

@Slf4j
@Configuration
public class JobConfig {
    private final JobBuilderFactory jobs;

    @Autowired
    public JobConfig(JobBuilderFactory jobs) {
        this.jobs = jobs;
    }

    @Bean
    public Job jobConsumeTickerAndSerialize(@Qualifier("consumeTicker") Step consumeTicker) {
        return jobs.get(JOB_AMQP_CONSUME_TICKER)
                .incrementer(getJobParametersIncrementer())
                .flow(consumeTicker)
                .end()
                .build();
    }

    @Bean
    public Job jobConsumeCandleAndSerialize(@Qualifier("consumeCandle") Step consumeCandle) {
        return jobs.get(JOB_AMQP_CONSUME_CANDLE)
                .incrementer(getJobParametersIncrementer())
                .flow(consumeCandle)
                .end()
                .build();
    }

    @Bean
    public JobParametersIncrementer getJobParametersIncrementer() {
        return new RunIdIncrementer();
    }
}
