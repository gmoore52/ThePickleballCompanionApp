package edu.missouristate.thepickleballcompanionapp.domain.dto;

import java.sql.Timestamp;

public class UserDTO {
    private String userName;
    private String userFullName;
    private String emailAddress;
    private String password;
    private String profileImgLoc;
    private Integer skillLevel;
    private Timestamp accCreationDate;

    // Default constructor
    public UserDTO() {}

    // Parameterized constructor
    public UserDTO(String userName, String userFullName, String emailAddress,
                   String password, String profileImgLoc, Integer skillLevel,
                   Timestamp accCreationDate) {
        this.userName = userName;
        this.userFullName = userFullName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.profileImgLoc = profileImgLoc;
        this.skillLevel = skillLevel;
        this.accCreationDate = accCreationDate;
    }

    // Getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
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
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", profileImgLoc='" + profileImgLoc + '\'' +
                ", skillLevel=" + skillLevel +
                ", accCreationDate=" + accCreationDate +
                '}';
    }
}
