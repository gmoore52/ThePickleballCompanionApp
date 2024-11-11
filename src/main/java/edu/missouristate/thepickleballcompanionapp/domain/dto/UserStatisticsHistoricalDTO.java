package edu.missouristate.thepickleballcompanionapp.domain.dto;

import java.sql.Timestamp;

public class UserStatisticsHistoricalDTO {
    private String username;

    private Timestamp maxTimestamp;

    private Timestamp loadTimestamp;

    private Integer totalGames;

    private Long gamesWon;

    private Long gamesLost;

    private Float winLossRatio;

    private String playerMostPlayedWith;

    private String opponentMostLostTo;

    private Integer lossesToStrongestOpponent;

    private Integer mostFrequentLocationID;

    public UserStatisticsHistoricalDTO(String username,
                                       Timestamp maxTimestamp,
                                       Timestamp loadTimestamp,
                                       Integer totalGames,
                                       Long gamesWon,
                                       Long gamesLost,
                                       Float winLossRatio,
                                       String playerMostPlayedWith,
                                       String opponentMostLostTo,
                                       Integer lossesToStrongestOpponent,
                                       Integer mostFrequentLocationID) {
        this.username = username;
        this.maxTimestamp = maxTimestamp;
        this.loadTimestamp = loadTimestamp;
        this.totalGames = totalGames;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.winLossRatio = winLossRatio;
        this.playerMostPlayedWith = playerMostPlayedWith;
        this.opponentMostLostTo = opponentMostLostTo;
        this.lossesToStrongestOpponent = lossesToStrongestOpponent;
        this.mostFrequentLocationID = mostFrequentLocationID;
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

    public Timestamp getLoadTimestamp() {
        return loadTimestamp;
    }

    public void setLoadTimestamp(Timestamp loadTimestamp) {
        this.loadTimestamp = loadTimestamp;
    }

    public Integer getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(Integer totalGames) {
        this.totalGames = totalGames;
    }

    public Long getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Long gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Long getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(Long gamesLost) {
        this.gamesLost = gamesLost;
    }

    public Float getWinLossRatio() {
        return winLossRatio;
    }

    public void setWinLossRatio(Float winLossRatio) {
        this.winLossRatio = winLossRatio;
    }

    public String getPlayerMostPlayedWith() {
        return playerMostPlayedWith;
    }

    public void setPlayerMostPlayedWith(String playerMostPlayedWith) {
        this.playerMostPlayedWith = playerMostPlayedWith;
    }

    public String getOpponentMostLostTo() {
        return opponentMostLostTo;
    }

    public void setOpponentMostLostTo(String opponentMostLostTo) {
        this.opponentMostLostTo = opponentMostLostTo;
    }

    public Integer getLossesToStrongestOpponent() {
        return lossesToStrongestOpponent;
    }

    public void setLossesToStrongestOpponent(Integer lossesToStrongestOpponent) {
        this.lossesToStrongestOpponent = lossesToStrongestOpponent;
    }

    public Integer getMostFrequentLocationID() {
        return mostFrequentLocationID;
    }

    public void setMostFrequentLocationID(Integer mostFrequentLocationID) {
        this.mostFrequentLocationID = mostFrequentLocationID;
    }

    @Override
    public String toString() {
        return "UserStatisticsHistoricalDTO{" +
                "username='" + username + '\'' +
                ", maxTimestamp=" + maxTimestamp +
                ", loadTimestamp=" + loadTimestamp +
                ", totalGames=" + totalGames +
                ", gamesWon=" + gamesWon +
                ", gamesLost=" + gamesLost +
                ", winLossRatio=" + winLossRatio +
                ", playerMostPlayedWith='" + playerMostPlayedWith + '\'' +
                ", opponentMostLostTo='" + opponentMostLostTo + '\'' +
                ", lossesToStrongestOpponent=" + lossesToStrongestOpponent +
                ", mostFrequentLocationID=" + mostFrequentLocationID +
                '}';
    }
}
