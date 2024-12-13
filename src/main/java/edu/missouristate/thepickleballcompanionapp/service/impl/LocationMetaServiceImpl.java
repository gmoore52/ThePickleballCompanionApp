package edu.missouristate.thepickleballcompanionapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import edu.missouristate.thepickleballcompanionapp.dao.LocationMetaRepository;
import edu.missouristate.thepickleballcompanionapp.domain.dto.LocationMetaDTO;
import edu.missouristate.thepickleballcompanionapp.domain.LocationMeta;
import edu.missouristate.thepickleballcompanionapp.service.LocationMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationMetaServiceImpl implements LocationMetaService {

    @Autowired
    private LocationMetaRepository locationMetaRepository;

    @Override
    public List<LocationMetaDTO> getLocationMetas() {
        List<LocationMeta> locationMetas = locationMetaRepository.findAll();

        // Create a list to hold LocationMetaDTO objects
        List<LocationMetaDTO> locationMetaDTOs = new ArrayList<>();

        // Loop through each LocationMeta entity, convert it to LocationMetaDTO, and add to the list
        for (LocationMeta locationMeta : locationMetas) {
            LocationMetaDTO locationMetaDTO = new LocationMetaDTO(
                    locationMeta.getId(),
                    locationMeta.getAvailability(),
                    locationMeta.getShelter(),
                    locationMeta.getNetType()
            );
            locationMetaDTOs.add(locationMetaDTO);
        }

        // Return the list of DTOs
        return locationMetaDTOs;
    }
}
