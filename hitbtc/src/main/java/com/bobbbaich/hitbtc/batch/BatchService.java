package com.bobbbaich.hitbtc.batch;

import com.bobbbaich.hitbtc.transport.rabbit.RabbitCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Slf4j
@Service
public class BatchService {
    private static final String CONTEXT_PARAM_RESTARTABLE = "restartable";
    private static final String CONTEXT_PARAM_START_DATE = "startDate";
    private static final String CONTEXT_PARAM_QUEUE = "queue";

    private Job job;
    private JobLauncher jobLauncher;
    private RabbitCache rabbitCache;

    @Scheduled(fixedDelay = 1000)
    public void run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.debug("Jobs are running...");
        Set<String> queuesNames = rabbitCache.getQueuesNames();

        queuesNames.parallelStream().forEach(
                queue -> {
                    JobParameters jobParameters = getJobParameters(queue);
                    JobExecution jobExecution = null;
                    try {
                        jobExecution = jobLauncher.run(job, jobParameters);
                        log.debug("jobExecution's info: Id = {} ,status = {}",
                                jobExecution.getId(), jobExecution.getExitStatus());
                    } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private JobParameters getJobParameters(String queueName) {
        return new JobParametersBuilder()
                .addString(CONTEXT_PARAM_QUEUE, queueName)
                .addString(CONTEXT_PARAM_RESTARTABLE, "true")
                .addDate(CONTEXT_PARAM_START_DATE, new Date())
                .toJobParameters();
    }

    @Autowired
    public void setJob(Job job) {
        this.job = job;
    }

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setRabbitCache(RabbitCache rabbitCache) {
        this.rabbitCache = rabbitCache;
    }
}
