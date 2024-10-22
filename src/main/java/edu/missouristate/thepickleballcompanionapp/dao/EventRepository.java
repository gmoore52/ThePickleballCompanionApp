package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.EventRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer>, EventRepositoryCustom {
    public List<Event> getAllEvents();
}
