package com.exampleFour.demoFour;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.BatchStatus;

@SpringBootApplication
public class DemoFourApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(DemoFourApplication.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job importUserJob;

	public static void main(String[] args) {
		SpringApplication.run(DemoFourApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			logger.info("Starting the job with ageThreshold parameter...");
			JobExecution execution = jobLauncher.run(importUserJob, new JobParametersBuilder()
					.addLong("ageThreshold", 16L)
					.toJobParameters());

			if (execution.getStatus() == BatchStatus.COMPLETED) {
				logger.info("Job completed successfully");
			} else {
				logger.warn("Job completed with status: " + execution.getStatus());
			}
		} catch (Exception e) {
			logger.error("Job failed", e);
		}
	}
}
