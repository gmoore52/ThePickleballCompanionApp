package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@IdClass(UserStatisticsHistoricalCompositeKey.class)
@Table(name="USER_STATISTICS_HST")
public class UserStatisticsHistoric {
    @Id
    @Column(name="user_name")
    private String username;

    @Id
    @Column(name="max_timestamp")
    private Timestamp maxTimestamp;

    @Column(name="total_wins")
    private int totalWins;

    @Column(name="total_losses")
    private int totalLosses;

    @Column(name="total_games")
    private int totalGames;

    @Column(name="win_loss_ratio")
    private double winLossRatio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="most_freq_location")
    private Location mostFreqLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="most_freq_teammate")
    private User mostFreqTeammate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="strongest_opponent")
    private User strongestOpponent;

    @Column(name="most_losses_to_strongest_opponent")
    private int mostLossesToStrongestOpponent;

    public UserStatisticsHistoric(String username, Timestamp maxTimestamp, int totalWins, int totalLosses, int totalGames, double winLossRatio, Location mostFreqLocation, User mostFreqTeammate, User strongestOpponent, int mostLossesToStrongestOpponent) {
        this.username = username;
        this.maxTimestamp = maxTimestamp;
        this.totalWins = totalWins;
        this.totalLosses = totalLosses;
        this.totalGames = totalGames;
        this.winLossRatio = winLossRatio;
        this.mostFreqLocation = mostFreqLocation;
        this.mostFreqTeammate = mostFreqTeammate;
        this.strongestOpponent = strongestOpponent;
        this.mostLossesToStrongestOpponent = mostLossesToStrongestOpponent;
    }

    public UserStatisticsHistoric() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getMaxTimestamp() {
        return maxTimestamp;
    }

    public void setMaxTimestamp(Timestamp maxTimestamp) {
        this.maxTimestamp = maxTimestamp;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        this.totalLosses = totalLosses;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
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

    public User getMostFreqTeammate() {
        return mostFreqTeammate;
    }

    public void setMostFreqTeammate(User mostFreqTeammate) {
        this.mostFreqTeammate = mostFreqTeammate;
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
        return "UserStatisticsHistoric{" +
                "username='" + username + '\'' +
                ", maxTimestamp=" + maxTimestamp +
                ", totalWins=" + totalWins +
                ", totalLosses=" + totalLosses +
                ", totalGames=" + totalGames +
                ", winLossRatio=" + winLossRatio +
                ", mostFreqLocation=" + mostFreqLocation +
                ", mostFreqTeammate=" + mostFreqTeammate +
                ", strongestOpponent=" + strongestOpponent +
                ", mostLossesToStrongestOpponent=" + mostLossesToStrongestOpponent +
                '}';
    }
}
