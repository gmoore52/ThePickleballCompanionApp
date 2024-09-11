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
}
