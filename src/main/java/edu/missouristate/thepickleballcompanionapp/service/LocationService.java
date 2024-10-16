package edu.missouristate.thepickleballcompanionapp.service;

import java.util.List;

import edu.missouristate.thepickleballcompanionapp.domain.dto.LocationDTO;

public interface LocationService {
    public List<LocationDTO> getLocations();
}
