package edu.missouristate.thepickleballcompanionapp.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="GAMES")
public class Game {
    @Id
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
}