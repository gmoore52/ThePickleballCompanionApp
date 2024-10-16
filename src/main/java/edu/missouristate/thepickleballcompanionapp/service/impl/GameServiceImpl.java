package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.GameRepository;
import edu.missouristate.thepickleballcompanionapp.dao.LocationRepository;
import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.Game;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.dto.LoggedGame;
import edu.missouristate.thepickleballcompanionapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private LocationRepository locationRepository;


    public List<User> getUsers(){
        return userRepository.getAllUsers();
    }

    public User getUserById(String username){
        return userRepository.getUserById(username);
    }

    public boolean logGame(LoggedGame game){
        Game gameToLog = new Game();

        gameToLog.setGameId(-1);
        gameToLog.setGameDate(Timestamp.valueOf(game.getGameDate()));
        gameToLog.setTeam1Score(game.getUserScore());
        gameToLog.setTeam2Score(game.getOppScore());
        gameToLog.setLocation(locationRepository.getLocationById(game.getLocation()));
        gameToLog.setPlayer1Id(userRepository.getUserById(game.getPlayer1()));
        gameToLog.setPlayer2Id(userRepository.getUserById(game.getPlayer2()));
        gameToLog.setPlayer3Id(userRepository.getUserById(game.getPlayer3()));
        gameToLog.setPlayer4Id(userRepository.getUserById(game.getPlayer4()));

        return gameRepository.save(gameToLog) != null;
    }
}