package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.Game;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.dto.LoggedGame;
import edu.missouristate.thepickleballcompanionapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    @Autowired
    private GameService gameService;


    @GetMapping("game/users")
    public List<User> getUsers(){
        return gameService.getUsers();
    }

    @GetMapping("game/userById")
    public User getUserById(@RequestBody String username){
        return gameService.getUserById(username);
    }


    @PostMapping("game/logGame")
    public boolean logGame(@RequestBody LoggedGame game){

        return gameService.logGame(game);
    }


    @GetMapping("game/example")
    public String example() {
        return "Hello World!";
    }
}
