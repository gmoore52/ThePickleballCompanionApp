package edu.missouristate.thepickleballcompanionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class})
public class ThePickleballCompanionAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThePickleballCompanionAppApplication.class, args);
    }

}
