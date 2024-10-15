package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;


    @GetMapping("statistics/example")
    public String example() {
        return "Hello World!";
    }
}
