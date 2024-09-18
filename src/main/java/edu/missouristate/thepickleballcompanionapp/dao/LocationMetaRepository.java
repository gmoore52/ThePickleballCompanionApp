package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.LocationMetaRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.LocationMeta;
import org.springframework.data.repository.CrudRepository;

public interface LocationMetaRepository extends CrudRepository<LocationMeta, Integer>, LocationMetaRepositoryCustom {
}
