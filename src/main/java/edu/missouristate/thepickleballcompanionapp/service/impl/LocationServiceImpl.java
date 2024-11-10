package edu.missouristate.thepickleballcompanionapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import edu.missouristate.thepickleballcompanionapp.domain.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.missouristate.thepickleballcompanionapp.dao.LocationRepository;
import edu.missouristate.thepickleballcompanionapp.domain.Location;
import edu.missouristate.thepickleballcompanionapp.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<LocationDTO> getLocations() {
        List<Location> locations = locationRepository.findAll();

        // Create a list to hold LocationDTO objects
        List<LocationDTO> locationDTOs = new ArrayList<>();

        // Loop through each Location entity, convert it to LocationDTO, and add to the list
        for (Location location : locations) {
            LocationDTO locationDTO = new LocationDTO(
                    location.getId(),
                    location.getCourtName(),
                    location.getNumOfCourts(),
                    location.getAddress(),
                    location.getCoordinates(),
                    location.getMeta() != null ? location.getMeta().getId().longValue() : null,  // Handling possible null for meta
                    location.getCourtPic()
            );
            locationDTOs.add(locationDTO);
        }

        // Return the list of DTOs
        return locationDTOs;
    }

}
