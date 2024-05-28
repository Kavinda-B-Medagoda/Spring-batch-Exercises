package com.DbToCsv.DbToCsv.listners;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class LoggingJobExecutionListener {

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before job logic...");
        // Perform any setup or logic before the job starts
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After job logic...");
        // Perform any cleanup or logic after the job completes
    }
}