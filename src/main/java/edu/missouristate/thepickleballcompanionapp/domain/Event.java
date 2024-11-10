package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "event_start")
    private Timestamp eventStart;

    @Column(name = "event_end")
    private Timestamp eventEnd;

    @Column(name = "event_loc")
    private Integer eventLoc;

    @Column(name = "event_title")
    private String eventTitle;

    @Column(name = "event_desc")
    private String eventDesc;

    // Getters and Setters

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Timestamp getEventStart() {
        return eventStart;
    }

    public void setEventStart(Timestamp eventStart) {
        this.eventStart = eventStart;
    }

    public Timestamp getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Timestamp eventEnd) {
        this.eventEnd = eventEnd;
    }

    public Integer getEventLoc() {
        return eventLoc;
    }

    public void setEventLoc(Integer eventLoc) {
        this.eventLoc = eventLoc;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventStart=" + eventStart +
                ", eventEnd=" + eventEnd +
                ", eventLoc=" + eventLoc +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDesc='" + eventDesc + '\'' +
                '}';
    }
}