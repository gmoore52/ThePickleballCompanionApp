package edu.missouristate.thepickleballcompanionapp.dao.custom;

import edu.missouristate.thepickleballcompanionapp.domain.Request;
import edu.missouristate.thepickleballcompanionapp.domain.User;

import java.util.List;

public interface RequestRepositoryCustom {
    public List<Request> findByStatusAndOriginOrDestination(String status, User user);
}
