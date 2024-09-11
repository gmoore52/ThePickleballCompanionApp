package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="REQUESTS")
public class Request {
    @Id
    @Column(name="request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    @Column(name="request_type")
    private String requestType;

    @Column(name="request_date")
    private Timestamp requestDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="origin_id")
    private User origin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id")
    private Event event;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="destination_id")
    private User destination;

    @Column(name="request_status")
    private Integer requestAcceoted;
}
