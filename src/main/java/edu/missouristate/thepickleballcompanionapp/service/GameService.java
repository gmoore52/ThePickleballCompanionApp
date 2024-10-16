package edu.missouristate.thepickleballcompanionapp.service;

import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.dto.LoggedGame;

import java.util.List;

public interface GameService {
    public List<User> getUsers();

    public User getUserById(String username);

    public boolean logGame(LoggedGame game);
}
