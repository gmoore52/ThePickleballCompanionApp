package edu.missouristate.thepickleballcompanionapp.service;

import edu.missouristate.thepickleballcompanionapp.domain.Event;
import edu.missouristate.thepickleballcompanionapp.domain.dto.EventDTO;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    Boolean logEvent(EventDTO game);
}
