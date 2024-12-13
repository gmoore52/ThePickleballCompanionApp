package edu.missouristate.thepickleballcompanionapp.domain.dto;

public class UserStatisticsDTO {
    private String username;

    private int totalGames;

    private int totalWins;

    private int totalLosses;

    private float winLossRatio;

    private int mostFrequentLocationID;

    private String mostFrequentTeammateUsername;

    private String strongestOpponentUsername;

    private int mostLossesToStrongestOpponent;

    public UserStatisticsDTO(String username,
                             int totalGames,
                             int totalWins,
                             int totalLosses,
                             float winLossRatio,
                             int mostFrequentLocationID,
                             String mostFrequentTeammateUsername,
                             String strongestOpponentUsername,
                             int mostLossesToStrongestOpponent) {
        this.username = username;
        this.totalGames = totalGames;
        this.totalWins = totalWins;
        this.totalLosses = totalLosses;
        this.winLossRatio = winLossRatio;
        this.mostFrequentLocationID = mostFrequentLocationID;
        this.mostFrequentTeammateUsername = mostFrequentTeammateUsername;
        this.strongestOpponentUsername = strongestOpponentUsername;
        this.mostLossesToStrongestOpponent = mostLossesToStrongestOpponent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        this.totalLosses = totalLosses;
    }

    public float getWinLossRatio() {
        return winLossRatio;
    }

    public void setWinLossRatio(float winLossRatio) {
        this.winLossRatio = winLossRatio;
    }

    public int getMostFrequentLocationID() {
        return mostFrequentLocationID;
    }

    public void setMostFrequentLocationID(int mostFrequentLocationID) {
        this.mostFrequentLocationID = mostFrequentLocationID;
    }

    public String getMostFrequentTeammateUsername() {
        return mostFrequentTeammateUsername;
    }

    public void setMostFrequentTeammateUsername(String mostFrequentTeammateUsername) {
        this.mostFrequentTeammateUsername = mostFrequentTeammateUsername;
    }

    public String getStrongestOpponentUsername() {
        return strongestOpponentUsername;
    }

    public void setStrongestOpponentUsername(String strongestOpponentUsername) {
        this.strongestOpponentUsername = strongestOpponentUsername;
    }

    public int getMostLossesToStrongestOpponent() {
        return mostLossesToStrongestOpponent;
    }

    public void setMostLossesToStrongestOpponent(int mostLossesToStrongestOpponent) {
        this.mostLossesToStrongestOpponent = mostLossesToStrongestOpponent;
    }

    @Override
    public String toString() {
        return "UserStatisticsDTO{" +
                "username='" + username + '\'' +
                ", totalGames=" + totalGames +
                ", totalWins=" + totalWins +
                ", totalLosses=" + totalLosses +
                ", winLossRatio=" + winLossRatio +
                ", mostFrequentLocationID=" + mostFrequentLocationID +
                ", mostFrequentTeammateUsername='" + mostFrequentTeammateUsername + '\'' +
                ", strongestOpponentUsername='" + strongestOpponentUsername + '\'' +
                ", mostLossesToStrongestOpponent=" + mostLossesToStrongestOpponent +
                '}';
    }
}
