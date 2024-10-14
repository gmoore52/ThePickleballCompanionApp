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
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

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

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getDestination() {
        return destination;
    }

    public void setDestination(User destination) {
        this.destination = destination;
    }

    public Integer getRequestAcceoted() {
        return requestAcceoted;
    }

    public void setRequestAcceoted(Integer requestAcceoted) {
        this.requestAcceoted = requestAcceoted;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", requestType='" + requestType.toString() + '\'' +
                ", requestDate=" + requestDate +
                ", origin=" + origin +
                ", event=" + event +
                ", destination=" + destination +
                ", requestAcceoted=" + requestAcceoted +
                '}';
    }
}
