package com.bobbbaich.hitbtc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("batch")
public class JobLauncherController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @GetMapping("/run")
    public void handle() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("restartable", "true")
                .addDate("start_date", new Date())
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        log.debug("jobExecution's info: Id = {} ,status = {}", jobExecution.getId(), jobExecution.getExitStatus());

    }
}