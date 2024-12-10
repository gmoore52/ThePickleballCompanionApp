package edu.missouristate.thepickleballcompanionapp.domain.dto;

public class LocationMetaDTO {
    private Integer id;
    private String availability;
    private String shelter;
    private String netType;

    public LocationMetaDTO() {
    }

    public LocationMetaDTO(Integer id, String availability, String shelter, String netType) {
        this.id = id;
        this.availability = availability;
        this.shelter = shelter;
        this.netType = netType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getShelter() {
        return shelter;
    }

    public void setShelter(String shelter) {
        this.shelter = shelter;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    @Override
    public String toString() {
        return "LocationMetaDTO{" +
                "id=" + id +
                ", availability='" + availability + '\'' +
                ", shelter='" + shelter + '\'' +
                ", netType='" + netType + '\'' +
                '}';
    }
}
