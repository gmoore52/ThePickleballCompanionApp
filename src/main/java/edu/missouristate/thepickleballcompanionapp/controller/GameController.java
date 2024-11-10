package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.Game;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.GameDTO;
import edu.missouristate.thepickleballcompanionapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/userById")
    public User getUserById(@RequestBody String username) {
        return gameService.getUserById(username);
    }

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    @ResponseBody
    public List<GameDTO> getUserGames(@RequestParam("username") String username) {
        return gameService.getUserGames(username);
    }

    @RequestMapping(value = "/logGame", method = RequestMethod.POST)
    @ResponseBody
    public Boolean logGame(@RequestBody GameDTO game) {
        return gameService.logGame(game);
    }

    @GetMapping("game/example")
    public String example() {
        return "Hello World!";
    }
}
