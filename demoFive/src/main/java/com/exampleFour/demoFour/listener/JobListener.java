package com.exampleFour.demoFour.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener {
    @Override
    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().putInt("processedCount", 0);
    }

    @Override
    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        int processedCount = jobExecution.getExecutionContext().getInt("processedCount");
        System.out.println("Total processed count: " + processedCount);
    }
}
