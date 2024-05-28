package com.exampleFour.demoFour.writer;

import com.exampleFour.demoFour.dto.PersonDTO;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class PersonWriter implements ItemWriter<PersonDTO> {

    private StepExecution stepExecution;

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(List<? extends PersonDTO> list) throws Exception {
        int processedCount = stepExecution.getJobExecution().getExecutionContext().getInt("processedCount");
        System.out.println("Processed Count in writer: " + processedCount);
    }
}
