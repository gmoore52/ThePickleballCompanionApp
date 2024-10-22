package edu.missouristate.thepickleballcompanionapp.domain.dto;

import java.sql.Timestamp;

public class EventDTO {
    private Integer eventId;
    private Timestamp eventStart;
    private Timestamp eventEnd;
    private Integer eventLoc;
    private String eventTitle;
    private String eventDesc;
    private Integer locationId;

    // Constructors
    public EventDTO() {
    }

    public EventDTO(Integer eventId, java.sql.Timestamp eventStart, java.sql.Timestamp eventEnd, Integer eventLoc, String eventTitle, String eventDesc, Integer locationId) {
        this.eventId = eventId;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.eventLoc = eventLoc;
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.locationId = locationId;
    }

    // Getters and Setters
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public java.sql.Timestamp getEventStart() {
        return eventStart;
    }

    public void setEventStart(java.sql.Timestamp eventStart) {
        this.eventStart = eventStart;
    }

    public java.sql.Timestamp getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(java.sql.Timestamp eventEnd) {
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

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    // toString Method
    @Override
    public String toString() {
        return "EventDTO{" +
                "eventId=" + eventId +
                ", eventStart=" + eventStart +
                ", eventEnd=" + eventEnd +
                ", eventLoc=" + eventLoc +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDesc='" + eventDesc + '\'' +
                ", locationId=" + locationId +
                '}';
    }
}