package com.bobbbaich.hitbtc.config;

import com.bobbbaich.hitbtc.batch.MessageProcessor;
import com.bobbbaich.hitbtc.batch.MessageStreamReader;
import com.bobbbaich.hitbtc.model.Candle;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
@EnableBatchProcessing
public class BatchConfig {
    public static final String JOB_AMQP_CONSUME = "jobConsumeCandleAndSerialize";
    private static final int CHUNK_SIZE = 1;

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;

    @Autowired
    public BatchConfig(JobBuilderFactory jobs, StepBuilderFactory steps) {
        this.jobs = jobs;
        this.steps = steps;
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();

        return jobLauncher;
    }

    @Bean
    public Job jobConsumeCandleAndSerialize(@Qualifier("consumeCandle") Step consumeCandle) {
        return jobs.get(JOB_AMQP_CONSUME)
                .incrementer(getJobParametersIncrementer())
                .flow(consumeCandle)
                .end()
                .build();
    }

    @Bean
    public JobParametersIncrementer getJobParametersIncrementer() {
        return new RunIdIncrementer();
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
}
