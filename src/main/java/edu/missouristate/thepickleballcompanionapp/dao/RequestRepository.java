package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.RequestRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Integer>, RequestRepositoryCustom {

    // Find a request by sender (origin) and receiver (destination)
    Request findByOriginAndDestination(String originUsername, String destUsername);

    // Check if a request already exists between two users (sent or received)
    boolean existsByOriginAndDestination(String originUsername, String destUsername);

    // find friends for selected user
    List<Request> findByRequestStatusAndOriginUsernameOrDestinationUsername(String status, String username);
}

