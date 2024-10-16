package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="GAMES")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="game_id")
    private Integer gameId;

    @Column(name="game_date")
    private Timestamp gameDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="player_1_id")
    private User player1Id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="player_2_id")
    private User player2Id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="player_3_id")
    private User player3Id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="player_4_id")
    private User player4Id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="location_id")
    private Location location;

    @Column(name="team_1_score")
    private Integer team1Score;

    @Column(name="team_2_score")
    private Integer team2Score;

    @Column(name="description")
    private String description;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Timestamp getGameDate() {
        return gameDate;
    }

    public void setGameDate(Timestamp gameDate) {
        this.gameDate = gameDate;
    }

    public User getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(User player1Id) {
        this.player1Id = player1Id;
    }

    public User getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(User player2Id) {
        this.player2Id = player2Id;
    }

    public User getPlayer3Id() {
        return player3Id;
    }

    public void setPlayer3Id(User player3Id) {
        this.player3Id = player3Id;
    }

    public User getPlayer4Id() {
        return player4Id;
    }

    public void setPlayer4Id(User player4Id) {
        this.player4Id = player4Id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    public void setDescription(String description) { this.description = description; }

    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", gameDate=" + gameDate +
                ", player1Id=" + player1Id +
                ", player2Id=" + player2Id +
                ", player3Id=" + player3Id +
                ", player4Id=" + player4Id +
                ", location=" + location +
                ", team1Score=" + team1Score +
                ", team2Score=" + team2Score +
                ", description='" + description + '\'' +
                '}';
    }
}