package edu.missouristate.thepickleballcompanionapp.dto;

//gameDate
//:
//        "2024-10-10"
//location
//:
//        "Westside Park"
//notes
//:
//        null
//userScore
//:
//        "11"
//oppScore
//:
//        "1"
//player1
//:
//        "must_select"
//player2
//:
//        "sophia_moore"
//player3
//:
//        "Jane Smith - (jane_smith)"
//player4
//:
//        "Emily Davis - (emily_davis)"


public class LoggedGame {
    private String gameDate;

    private Long location;

    private String notes;

    private Integer userScore;

    private Integer oppScore;

    private String player1;

    private String player2;

    private String player3;

    private String player4;

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getUserScore(){
        return userScore;
    }

    public void setUserScore(Integer userScore){
        this.userScore = userScore;
    }

    public Integer getOppScore() {
        return oppScore;
    }

    public void setOppScore(Integer oppScore) {
        this.oppScore = oppScore;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer4() {
        return player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
    }

    @Override
    public String toString() {
        return "LoggedGame{" +
                "gameDate='" + gameDate + '\'' +
                ", location='" + location + '\'' +
                ", notes='" + notes + '\'' +
                ", oppScore=" + oppScore +
                ", player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", player3='" + player3 + '\'' +
                ", player4='" + player4 + '\'' +
                '}';
    }
}
