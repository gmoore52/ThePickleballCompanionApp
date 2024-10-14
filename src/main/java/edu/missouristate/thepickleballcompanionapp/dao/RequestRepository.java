package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.RequestRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Integer>, RequestRepositoryCustom {
}
