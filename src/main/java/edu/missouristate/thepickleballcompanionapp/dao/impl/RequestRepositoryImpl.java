package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.RequestRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.QRequest;
import edu.missouristate.thepickleballcompanionapp.domain.Request;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestRepositoryImpl extends QuerydslRepositorySupport implements RequestRepositoryCustom {
    QRequest request = QRequest.request;


    public RequestRepositoryImpl() {
        super(QRequest.class);
    }

    public List<Request> findByStatusAndOriginOrDestination(String status, User user) {
        return from(request)
                .select(request)
                .where(request.status.eq(status)
                        .and(request.origin.eq(user).or(request.destination.eq(user))))
                .fetch();
    }

    public List<Request> findByStatusOriginOnly(String status, User user) {
        return from(request)
                .select(request)
                .where(request.status.eq(status)
                        .and(request.destination.eq(user)))
                .fetch();
    }
}
