package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

@Entity
@Table(name="LOCATION_META")
public class LocationMeta {
    @Id
    @Column(name="meta_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="availability")
    private String availability;

    @Column(name="shelter")
    private String shelter;

    @Column(name="net_type")
    private String netType;

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
        return "LocationMeta{" +
                "id=" + id +
                ", availability='" + availability + '\'' +
                ", shelter='" + shelter + '\'' +
                ", netType='" + netType + '\'' +
                '}';
    }
}
