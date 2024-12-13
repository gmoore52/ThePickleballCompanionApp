package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

@Entity
@Table(name="USER_STATISTICS")
public class UserStatistics {
    @Id
    @Column(name="USER_NAME")
    private String userName;

    @Column(name="TOTAL_GAMES")
    private int totalGames;

    @Column(name="TOTAL_WINS")
    private int totalWins;

    @Column(name="TOTAL_LOSSES")
    private int totalLosses;

    @Column(name="WIN_LOSS_RATIO")
    private double winLossRatio;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MOST_FREQ_LOCATION")
    private Location mostFreqLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MOST_FREQ_TEAMATE")
    private User mostFreqTeamate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="STRONGEST_OPPONENT")
    private User strongestOpponent;

    @Column(name="MOST_LOSSES_TO_STRONGEST_OPPONENT")
    private int mostLossesToStrongestOpponent;


    public UserStatistics(String userName, int totalGames, int totalWins, double winLossRatio, Location mostFreqLocation, User mostFreqTeamate, User strongestOpponent, int mostLossesToStrongestOpponent) {
        this.userName = userName;
        this.totalGames = totalGames;
        this.totalWins = totalWins;
        this.winLossRatio = winLossRatio;
        this.mostFreqLocation = mostFreqLocation;
        this.mostFreqTeamate = mostFreqTeamate;
        this.strongestOpponent = strongestOpponent;
        this.mostLossesToStrongestOpponent = mostLossesToStrongestOpponent;
    }

    public UserStatistics() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        this.totalLosses = totalLosses;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public double getWinLossRatio() {
        return winLossRatio;
    }

    public void setWinLossRatio(double winLossRatio) {
        this.winLossRatio = winLossRatio;
    }

    public Location getMostFreqLocation() {
        return mostFreqLocation;
    }

    public void setMostFreqLocation(Location mostFreqLocation) {
        this.mostFreqLocation = mostFreqLocation;
    }

    public User getMostFreqTeamate() {
        return mostFreqTeamate;
    }

    public void setMostFreqTeamate(User mostFreqTeamate) {
        this.mostFreqTeamate = mostFreqTeamate;
    }

    public User getStrongestOpponent() {
        return strongestOpponent;
    }

    public void setStrongestOpponent(User strongestOpponent) {
        this.strongestOpponent = strongestOpponent;
    }

    public int getMostLossesToStrongestOpponent() {
        return mostLossesToStrongestOpponent;
    }

    public void setMostLossesToStrongestOpponent(int mostLossesToStrongestOpponent) {
        this.mostLossesToStrongestOpponent = mostLossesToStrongestOpponent;
    }

    @Override
    public String toString() {
        return "UserStatistics{" +
                "userName='" + userName + '\'' +
                ", totalGames=" + totalGames +
                ", totalWins=" + totalWins +
                ", winLossRatio=" + winLossRatio +
                ", mostFreqLocation=" + mostFreqLocation +
                ", mostFreqTeamate=" + mostFreqTeamate +
                ", strongestOpponent=" + strongestOpponent +
                ", mostLossesToStrongestOpponent=" + mostLossesToStrongestOpponent +
                '}';
    }
}
