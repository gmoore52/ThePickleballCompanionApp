package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="EVENTS")
public class Event {
    @Id
    @Column(name="event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @Column(name="event_start")
    private Timestamp eventStart;

    @Column(name="event_end")
    private Timestamp eventEnd;

    @Column(name="event_desc")
    private String eventDesc;
}
