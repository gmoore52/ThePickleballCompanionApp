package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.EventRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Event;
import edu.missouristate.thepickleballcompanionapp.domain.QEvent;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventRepositoryImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {
    QEvent event = QEvent.event;

    public EventRepositoryImpl() {
        super(QEvent.class);
    }

    @Override
    public List<Event> getAllEvents(){
        return from(event)
                .fetch();
    }
}
