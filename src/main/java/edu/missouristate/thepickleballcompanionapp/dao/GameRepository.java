package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.GameRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer>, GameRepositoryCustom {
}
