package edu.missouristate.thepickleballcompanionapp.dao.custom;

import edu.missouristate.thepickleballcompanionapp.domain.Game;

import java.util.List;

public interface GameRepositoryCustom {
    List<Game> getUserGames(String username);
}
