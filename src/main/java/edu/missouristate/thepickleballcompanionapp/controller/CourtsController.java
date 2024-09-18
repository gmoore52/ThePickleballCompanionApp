package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourtsController {
    @Autowired
    private CourtService courtService;


    @GetMapping("court/example")
    public String example() {
        return "Hello World!";
    }
}
