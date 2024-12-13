package edu.missouristate.thepickleballcompanionapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.missouristate.thepickleballcompanionapp.domain.dto.LocationMetaDTO;
import edu.missouristate.thepickleballcompanionapp.service.LocationMetaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LocationMetaController {

    @Autowired
    private LocationMetaService locationService;

    @GetMapping("/data/locationmeta")
    public List<LocationMetaDTO> getLocations() {
        return locationService.getLocationMetas();
    }
}
