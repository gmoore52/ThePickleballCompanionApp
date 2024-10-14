package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

@Entity
@Table(name="LOCATIONS")
public class Location {
    @Id
    @Column(name="loc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="court_name")
    private String courtName;

    @Column(name="num_of_courts")
    private Integer numOfCourts;

    @Column(name="address")
    private String address;

    @Column(name="coordinates")
    private String coordinates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="court_meta_id")
    private LocationMeta meta;

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

    public LocationMeta getMeta() {
        return meta;
    }

    public void setMeta(LocationMeta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", courtName='" + courtName + '\'' +
                ", numOfCourts=" + numOfCourts +
                ", address='" + address + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", meta=" + meta +
                '}';
    }
}
