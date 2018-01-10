package com.bobbbaich.hitbtc.config;

import com.google.gson.JsonObject;
import org.kurento.jsonrpc.message.Request;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
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
    protected Step step1(ItemReader<Request<JsonObject>> reader, ItemProcessor<Request<JsonObject>, String> processor) {
        return steps.get("step1")
                .<Request<JsonObject>, String>chunk(5)
                .reader(reader)
                .processor(processor)
                .build();

    }

    @Bean
    public ItemReader<Request<JsonObject>> itemReader(AmqpTemplate amqpTemplate) {
        return new AmqpItemReader<>(amqpTemplate);
    }
}
