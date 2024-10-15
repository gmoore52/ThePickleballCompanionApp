package edu.missouristate.thepickleballcompanionapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.missouristate.thepickleballcompanionapp.dao.custom.LocationRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Location;

public interface LocationRepository extends CrudRepository<Location, Integer>, LocationRepositoryCustom {
    public List<Location> findAll();
}
