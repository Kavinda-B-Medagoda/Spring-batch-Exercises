package com.exampleFour.demoFour.reader;

import com.exampleFour.demoFour.model.Person;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;

@Configuration
public class ReaderConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Bean
    @StepScope
    public JpaPagingItemReader<Person> reader(@Value("#{jobParameters['ageThreshold']}") Long ageThreshold) {
        if (ageThreshold == null) {
            ageThreshold = 0L;
        }
        return new JpaPagingItemReaderBuilder<Person>()
                .name("personReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM Person p WHERE p.age < :ageThreshold")
                .parameterValues(Collections.singletonMap("ageThreshold", ageThreshold.intValue()))
                .pageSize(10)
                .build();
    }
}
