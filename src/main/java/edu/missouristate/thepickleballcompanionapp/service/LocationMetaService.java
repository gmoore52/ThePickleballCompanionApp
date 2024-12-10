package edu.missouristate.thepickleballcompanionapp.service;

import edu.missouristate.thepickleballcompanionapp.domain.dto.LocationMetaDTO;

import java.util.List;

public interface LocationMetaService {
    List<LocationMetaDTO> getLocationMetas();
}
