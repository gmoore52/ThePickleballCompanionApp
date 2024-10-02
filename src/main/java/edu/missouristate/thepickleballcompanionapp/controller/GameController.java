package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;


    @GetMapping("game/users")
    public List<User> getUsers(){
        return gameService.getUsers();
    }

    @GetMapping("game/userById")
    public List<User> getUserById(@RequestBody int id){
        return null;
    }


    @GetMapping("game/example")
    public String example() {
        return "Hello World!";
    }
}
