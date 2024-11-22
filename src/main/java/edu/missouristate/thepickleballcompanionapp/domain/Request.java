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
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                ", status=" + status +
                '}';
    }
}
