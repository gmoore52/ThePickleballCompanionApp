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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImgLoc() {
        return profileImgLoc;
    }

    public void setProfileImgLoc(String profileImgLoc) {
        this.profileImgLoc = profileImgLoc;
    }

    public Integer getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(Integer skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Timestamp getAccCreationDate() {
        return accCreationDate;
    }

    public void setAccCreationDate(Timestamp accCreationDate) {
        this.accCreationDate = accCreationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", profileImgLoc='" + profileImgLoc + '\'' +
                ", skillLevel=" + skillLevel +
                ", accCreationDate=" + accCreationDate +
                '}';
    }
}
