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

}
