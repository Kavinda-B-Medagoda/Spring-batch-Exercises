package com.exampleFour.demoFour.processor;

import com.exampleFour.demoFour.dto.PersonDTO;
import com.exampleFour.demoFour.model.Person;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.core.StepExecution;

public class PersonProcessor implements ItemProcessor<Person, PersonDTO> {
    private StepExecution stepExecution;

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution){
        this.stepExecution = stepExecution;
    }

    @Override
    public PersonDTO process(Person person) throws Exception {
        int processedCount = stepExecution.getJobExecution().getExecutionContext().getInt("processedCount");
        stepExecution.getJobExecution().getExecutionContext().putInt("processedCount", processedCount+1);

        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setAge(person.getAge());

        return dto;
    }
}

