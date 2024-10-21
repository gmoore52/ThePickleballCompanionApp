package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.dto.LoggedGame;
import edu.missouristate.thepickleballcompanionapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return gameService.getUsers();
    }

    @GetMapping("game/userById")
    public User getUserById(@RequestBody String username) {
        return gameService.getUserById(username);
    }

    @RequestMapping(value = "/logGame", method = RequestMethod.POST)
    @ResponseBody
    public Boolean logGame(@RequestBody LoggedGame game) {
        return gameService.logGame(game);
    }

    @GetMapping("game/example")
    public String example() {
        return "Hello World!";
    }
}
