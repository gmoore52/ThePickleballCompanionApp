package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.Event;
import edu.missouristate.thepickleballcompanionapp.domain.dto.EventDTO;
import edu.missouristate.thepickleballcompanionapp.dto.LoggedGame;
import edu.missouristate.thepickleballcompanionapp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<Event> getEvents() {
        return eventService.getAllEvents();
    }

    @RequestMapping(value = "/logEvent", method = RequestMethod.POST)
    @ResponseBody
    public Boolean logGame(@RequestBody EventDTO game) {
        return eventService.logEvent(game);
    }
}
