package edu.missouristate.thepickleballcompanionapp.service;

import edu.missouristate.thepickleballcompanionapp.domain.Game;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.GameDTO;

import java.util.List;

public interface GameService {
    public List<User> getUsers();

    public User getUserById(String username);

    public boolean logGame(GameDTO game);

    public List<GameDTO> getUserGames(String username);
}
