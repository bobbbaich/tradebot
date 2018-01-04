package com.bobbbaich.hitbtc.config;

import com.bobbbaich.hitbtc.model.Symbol;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
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
                .get("ajob")
                .start(step1)
                .build();
    }

    @Bean
    protected Step step1(ItemReader<Symbol> reader, ItemProcessor<Symbol, Symbol> processor) {
        return steps.get("step1")
                .<Symbol, Symbol>chunk(1)
                .reader(reader)
                .processor(processor)
                .build();

    }
}
