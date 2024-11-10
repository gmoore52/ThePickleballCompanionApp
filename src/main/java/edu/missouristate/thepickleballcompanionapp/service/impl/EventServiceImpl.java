package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.EventRepository;
import edu.missouristate.thepickleballcompanionapp.domain.Event;
import edu.missouristate.thepickleballcompanionapp.domain.dto.EventDTO;
import edu.missouristate.thepickleballcompanionapp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    public Boolean logEvent(EventDTO event) {
        Integer timeZoneDiffFromUTC = 5; // this is the hardcoded value we need for the events to work correctly in
                                         // Central time
        Event eventToLog = new Event();

        eventToLog.setEventId(-1);
        eventToLog.setEventDesc(event.getEventDesc());
        eventToLog.setEventLoc(event.getEventLoc());
        eventToLog.setEventTitle(event.getEventTitle());

        // adjusting the time for timezones here
        Timestamp tempStart = new Timestamp(event.getEventStart().getTime() + (timeZoneDiffFromUTC * 1000 * 60 * 60));
        Timestamp tempEnd = new Timestamp(event.getEventEnd().getTime() + (timeZoneDiffFromUTC * 1000 * 60 * 60));

        eventToLog.setEventStart(tempStart);
        eventToLog.setEventEnd(tempEnd);

        eventRepository.save(eventToLog);
        return true;
    }

}
