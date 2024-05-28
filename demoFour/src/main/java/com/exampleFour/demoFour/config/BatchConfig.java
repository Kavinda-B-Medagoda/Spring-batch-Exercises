package com.exampleFour.demoFour.config;

import com.exampleFour.demoFour.CustomJobParametersValidator;
import com.exampleFour.demoFour.dto.PersonDTO;
import com.exampleFour.demoFour.listener.JobListener;
import com.exampleFour.demoFour.model.Person;
import com.exampleFour.demoFour.processor.PersonProcessor;
import com.exampleFour.demoFour.reader.ReaderConfig;
import com.exampleFour.demoFour.writer.PersonWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobListener jobListener;

    @Autowired
    private ReaderConfig readerConfig;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .validator(new CustomJobParametersValidator())
                .listener(jobListener)
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, PersonDTO>chunk(10)
                .reader(readerConfig.reader(null))
                .processor(personProcessor())
                .writer(personWriter())
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public PersonProcessor personProcessor() {
        return new PersonProcessor();
    }

    @Bean
    public PersonWriter personWriter() {
        return new PersonWriter();
    }
}
