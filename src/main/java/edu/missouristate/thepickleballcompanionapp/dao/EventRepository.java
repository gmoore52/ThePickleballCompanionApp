package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.EventRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer>, EventRepositoryCustom {
}
