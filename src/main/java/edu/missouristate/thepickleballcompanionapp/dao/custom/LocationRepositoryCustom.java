package edu.missouristate.thepickleballcompanionapp.dao.custom;

import edu.missouristate.thepickleballcompanionapp.domain.Location;

public interface LocationRepositoryCustom {
    public Location getLocationById(Long id);
}
