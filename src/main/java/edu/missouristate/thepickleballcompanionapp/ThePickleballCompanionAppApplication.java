package edu.missouristate.thepickleballcompanionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class, SecurityAutoConfiguration.class})
public class ThePickleballCompanionAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThePickleballCompanionAppApplication.class, args);
    }

}
