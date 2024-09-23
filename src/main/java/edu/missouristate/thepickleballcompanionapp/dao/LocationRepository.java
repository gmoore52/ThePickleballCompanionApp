package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.LocationRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer>, LocationRepositoryCustom {
}
