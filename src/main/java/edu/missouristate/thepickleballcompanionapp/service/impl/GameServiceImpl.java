package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.GameRepository;
import edu.missouristate.thepickleballcompanionapp.dao.LocationRepository;
import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.Game;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.GameDTO;
import edu.missouristate.thepickleballcompanionapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private LocationRepository locationRepository;

    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(String username) {
        return userRepository.getUserById(username);
    }

    public boolean logGame(GameDTO game) {
        Game gameToLog = new Game();

        gameToLog.setGameId(-1);
        gameToLog.setGameDate(Timestamp.valueOf(game.getGameDate()));
        gameToLog.setTeam1Score(game.getUserScore());
        gameToLog.setTeam2Score(game.getOppScore());
        gameToLog.setDescription(game.getNotes());
        gameToLog.setLocation(locationRepository.getLocationById(game.getLocation()));
        gameToLog.setPlayer1(userRepository.getUserById(game.getPlayer1()));
        gameToLog.setPlayer2(userRepository.getUserById(game.getPlayer2()));
        if (game.getPlayer3() != null) {
            gameToLog.setPlayer3(userRepository.getUserById(game.getPlayer3()));
        }
        if (game.getPlayer4() != null) {
            gameToLog.setPlayer4(userRepository.getUserById(game.getPlayer4()));
        }

        gameRepository.save(gameToLog);
        return true;
    }

    @Override
    public List<GameDTO> getUserGames(String username) {
        List<Game> games = gameRepository.getUserGames(username);

        // Convert each Game entity to a GameDTO directly within the function
        return games.stream().map(game -> new GameDTO(
                game.getGameDate() != null ? game.getGameDate().toString() : null,            // Convert Timestamp to String
                game.getLocation() != null ? game.getLocation().getId() : null,               // Assuming Location has an ID
                game.getDescription(),                                                        // Description -> Notes
                game.getTeam1Score(),                                                         // User score
                game.getTeam2Score(),                                                         // Opponent score
                game.getPlayer1() != null ? game.getPlayer1().getUserName() : null,           // Player1's username
                game.getPlayer2() != null ? game.getPlayer2().getUserName() : null,           // Player2's username
                game.getPlayer3() != null ? game.getPlayer3().getUserName() : null,           // Player3's username
                game.getPlayer4() != null ? game.getPlayer4().getUserName() : null            // Player4's username
        )).collect(Collectors.toList());
    }
}
