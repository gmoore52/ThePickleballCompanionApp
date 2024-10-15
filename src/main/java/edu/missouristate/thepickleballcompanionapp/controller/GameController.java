package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    @Autowired
    private GameService gameService;


    @GetMapping("game/example")
    public String example() {
        return "Hello World!";
    }
}
