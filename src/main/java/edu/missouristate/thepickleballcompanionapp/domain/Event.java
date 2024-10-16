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
    
    @Column(name="location_id")
    private String locationId;

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

    public Integer getLocationId(){
        return locationId;
    }

    public void setEventEnd(Timestamp eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public void setLocationId(Integer locationId){
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventStart=" + eventStart +
                ", eventEnd=" + eventEnd +
                ", eventDesc='" + eventDesc +
                ", locationId='" + locationId + '\'' +
                '}';
    }
}
