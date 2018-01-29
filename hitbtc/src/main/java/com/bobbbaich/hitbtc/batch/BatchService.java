package com.bobbbaich.hitbtc.batch;

import com.bobbbaich.hitbtc.exception.TradeRuntimeException;
import com.bobbbaich.hitbtc.transport.rabbit.RabbitCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Set;

import static com.bobbbaich.hitbtc.config.batch.BatchConstants.JOB_AMQP_CONSUME_TICKER;


@Slf4j
@Service
public class BatchService {
    private static final String CONTEXT_PARAM_RESTARTABLE = "restartable";
    private static final String CONTEXT_PARAM_START_DATE = "startDate";
    private static final String CONTEXT_PARAM_QUEUE = "queue";
    private static final String RESTARTABLE = "true";

    private JobRegistry jobRegistry;
    private JobLauncher jobLauncher;
    private RabbitCache rabbitCache;

    @Scheduled(fixedDelay = 1000)
    public void consumeMessages() {
        try {
            Job job = jobRegistry.getJob(JOB_AMQP_CONSUME_TICKER);

            run(job);
        } catch (NoSuchJobException e) {
            log.error("There is no job to execute!", e);
            throw new TradeRuntimeException(e);
        }
    }

    private void run(Job job) {
        Assert.notNull(job, "Job cannot be null!");

        Set<String> queuesNames = rabbitCache.getQueuesNames();
        queuesNames.forEach(queue -> {
            JobParameters jobParameters = getJobParameters(queue);
            try {
                JobExecution jobExecution = jobLauncher.run(job, jobParameters);
                log.debug("jobExecution's info: Id = {} ,status = {}", jobExecution.getId(), jobExecution.getExitStatus());
            } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException e) {
                log.error("Error occurred when queues were being read!", e);
                throw new TradeRuntimeException(e);
            }
        });
    }

    private JobParameters getJobParameters(String queueName) {
        return new JobParametersBuilder()
                .addString(CONTEXT_PARAM_QUEUE, queueName)
                .addString(CONTEXT_PARAM_RESTARTABLE, RESTARTABLE)
                .addDate(CONTEXT_PARAM_START_DATE, new Date())
                .toJobParameters();
    }

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setRabbitCache(RabbitCache rabbitCache) {
        this.rabbitCache = rabbitCache;
    }

    @Autowired
    public void setJobRegistry(JobRegistry jobRegistry) {
        this.jobRegistry = jobRegistry;
    }
}
