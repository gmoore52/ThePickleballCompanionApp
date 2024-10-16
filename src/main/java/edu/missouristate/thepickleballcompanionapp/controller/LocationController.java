package edu.missouristate.thepickleballcompanionapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.missouristate.thepickleballcompanionapp.domain.dto.LocationDTO;
import edu.missouristate.thepickleballcompanionapp.service.LocationService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/data/locations")
    public List<LocationDTO> getLocations() {
        return locationService.getLocations();
    }
}
