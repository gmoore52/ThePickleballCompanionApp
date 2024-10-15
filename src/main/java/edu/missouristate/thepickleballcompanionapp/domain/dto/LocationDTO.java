package edu.missouristate.thepickleballcompanionapp.domain.dto;

public class LocationDTO {
    private Long id;
    private String courtName;
    private Integer numOfCourts;
    private String address;
    private String coordinates;
    private Long metaId;  // Assuming you only want to transfer the ID of LocationMeta

    // Default constructor
    public LocationDTO() {}

    // Parameterized constructor
    public LocationDTO(Long id, String courtName, Integer numOfCourts, String address, String coordinates, Long metaId) {
        this.id = id;
        this.courtName = courtName;
        this.numOfCourts = numOfCourts;
        this.address = address;
        this.coordinates = coordinates;
        this.metaId = metaId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public Integer getNumOfCourts() {
        return numOfCourts;
    }

    public void setNumOfCourts(Integer numOfCourts) {
        this.numOfCourts = numOfCourts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Long getMetaId() {
        return metaId;
    }

    public void setMetaId(Long metaId) {
        this.metaId = metaId;
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "id=" + id +
                ", courtName='" + courtName + '\'' +
                ", numOfCourts=" + numOfCourts +
                ", address='" + address + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", metaId=" + metaId +
                '}';
    }
}
