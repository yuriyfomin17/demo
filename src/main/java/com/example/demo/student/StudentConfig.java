package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student yuriy = new Student(
                    "Yuriy",
                    "fominyura2012@gmail.com",
                    LocalDate.of(1997, SEPTEMBER, 17)
            );
            Student alex = new Student(
                    "Alex",
                    "alex2012@gmail.com",
                    LocalDate.of(1999, SEPTEMBER, 17)
            );

            studentRepository.saveAll(List.of(yuriy, alex));
        };
    }
}
