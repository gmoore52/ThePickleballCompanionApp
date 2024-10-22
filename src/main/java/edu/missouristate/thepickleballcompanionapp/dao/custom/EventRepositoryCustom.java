package edu.missouristate.thepickleballcompanionapp.dao.custom;

import edu.missouristate.thepickleballcompanionapp.domain.Event;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> getAllEvents();
}
