package com.exampleFour.demoFour;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomJobParametersValidator implements JobParametersValidator {

    private static final Logger logger = LoggerFactory.getLogger(CustomJobParametersValidator.class);

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if (parameters == null) {
            throw new JobParametersInvalidException("Job parameters are null.");
        }

        Long ageThreshold = parameters.getLong("ageThreshold");
        if (ageThreshold == null) {
            throw new JobParametersInvalidException("The ageThreshold parameter is required.");
        }

        logger.info("ageThreshold parameter: {}", ageThreshold);
    }
}
