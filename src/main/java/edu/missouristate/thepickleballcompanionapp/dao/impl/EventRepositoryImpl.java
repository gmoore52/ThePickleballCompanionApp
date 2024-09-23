package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.EventRepository;
import edu.missouristate.thepickleballcompanionapp.dao.custom.EventRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.QEvent;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepositoryImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {
    QEvent event = QEvent.event;


    public EventRepositoryImpl() {
        super(QEvent.class);
    }
}
