package com.DbToCsv.DbToCsv.listners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class LoggingStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Step started at: " + stepExecution.getStartTime());
        // Add any setup or logic before the step starts
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Step finished at: " + stepExecution.getEndTime());
        // Add any cleanup or logic after the step completes
        return null; // Return null to use the default ExitStatus
    }
}