package edu.missouristate.thepickleballcompanionapp.service.impl;


import edu.missouristate.thepickleballcompanionapp.dao.EventRepository;
import edu.missouristate.thepickleballcompanionapp.domain.Event;
import edu.missouristate.thepickleballcompanionapp.domain.dto.EventDTO;
import edu.missouristate.thepickleballcompanionapp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    public Boolean logEvent(EventDTO event) {
        Event eventToLog = new Event();

        eventToLog.setEventId(-1);
        eventToLog.setEventDesc(event.getEventDesc());
        eventToLog.setEventLoc(event.getEventLoc());
        eventToLog.setEventTitle(event.getEventTitle());
        eventToLog.setEventStart((event.getEventStart()));
        eventToLog.setEventEnd((event.getEventEnd()));

        eventRepository.save(eventToLog);
        return true;
    }

}


