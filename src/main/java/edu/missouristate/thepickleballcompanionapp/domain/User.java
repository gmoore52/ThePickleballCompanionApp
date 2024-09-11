package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="USERS")
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name="email_addr")
    private String emailAddress;

    @Column(name="password")
    private String password;

    @Column(name="profile_img_loc")
    private String profileImgLoc;

    @Column(name="skill_lvl")
    private Integer skillLevel;

    @Column(name="acc_creation_date")
    private Timestamp accCreationDate;
}
