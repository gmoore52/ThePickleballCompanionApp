package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.RequestRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.QRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class RequestRepositoryImpl extends QuerydslRepositorySupport implements RequestRepositoryCustom {
    QRequest request = QRequest.request;


    public RequestRepositoryImpl() {
        super(QRequest.class);
    }
}
