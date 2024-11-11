package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.StatisticsRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.*;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsHistoricalDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class StatisticsRepositoryImpl extends QuerydslRepositorySupport implements StatisticsRepositoryCustom {
    QUserStatistics userStatistics = QUserStatistics.userStatistics;
    QGame game = QGame.game;


    public StatisticsRepositoryImpl() {
        super(QUserStatistics.class);
    }

    public UserStatistics getUserStatisticsByUsername(String username) {
        return from(userStatistics)
                .select(userStatistics)
                .where(userStatistics.userName.eq(username))
                .fetchOne();
    }

    public List<UserStatisticsHistoricalDTO> getHistoricStatistics(Timestamp timestamp, User user){
        List<UserStatisticsHistoricalDTO> historicStatistics = new ArrayList<>();
        Map<Timestamp, List<Game>> timestampGameMap = new HashMap<>();

        // Loop through each possible timestamp for a game to happen for this user
        for(Timestamp t : from(game)
                .select(game.gameDate).distinct()
                .where(game.player1.eq(user)
                        .or(game.player2.eq(user))
                        .or(game.player3.eq(user))
                        .or(game.player4.eq(user)))
                .fetch()){
            // Make a list of each game that is before or on the timestamp in this iteration
            List<Game> games = from(game)
                                .select(game)
                                .where(game.gameDate.loe(t)
                                        .and(game.player1.eq(user)
                                        .or(game.player2.eq(user)
                                        .or(game.player3.eq(user)
                                        .or(game.player4.eq(user))))))
                                .fetch();

            // Get dummy variables ready for generating the DTO
            String username = user.getUserName();
            int totalGames = games.size();
            long gamesWon = games.stream()
                    .filter(game -> {if(game.getPlayer1() == user || game.getPlayer3() == user){
                        return game.getTeam1Score() > game.getTeam2Score();}
                    else{
                        return game.getTeam1Score() < game.getTeam2Score();
                    }
                    }).count();
            long gamesLost = totalGames - gamesWon;
            float winLossRatio = ((float)gamesWon)/((float)gamesLost);
            Map<String, Integer> playersPlayedWith = new HashMap<>();
            Map<String, Integer> opponentsPlayedAgainst = new HashMap<>();
            Map<Integer, Integer> locationsPlayedAt = new HashMap<>();

            // For loop that will accumulate statistics for each game before the current timestamp
            for(Game g : games){
                // Add a new location or increment it
                locationsPlayedAt.put(Math.toIntExact(g.getLocation().getId()),
                                locationsPlayedAt.getOrDefault(
                                        Math.toIntExact(g.getLocation().getId()), 0) + 1);

                // Run a check for each case where a user is either player 1, 2, 3, or 4
                // Then add the players played with and the players lost to to their respective maps
                if(g.getPlayer1() == user){
                    playersPlayedWith.put(g.getPlayer3().getUserName(),
                            playersPlayedWith.getOrDefault(g.getPlayer3().getUserName(), 0) + 1);

                    if(g.getTeam1Score() < g.getTeam2Score()){
                        opponentsPlayedAgainst.put(g.getPlayer2().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer2().getUserName(), 0) + 1);
                        opponentsPlayedAgainst.put(g.getPlayer4().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer4().getUserName(), 0) + 1);
                    }
                }
                else if(g.getPlayer2() == user){
                    playersPlayedWith.put(g.getPlayer4().getUserName(),
                            playersPlayedWith.getOrDefault(g.getPlayer4().getUserName(), 0) + 1);

                    if(g.getTeam1Score() > g.getTeam2Score()){
                        opponentsPlayedAgainst.put(g.getPlayer1().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer1().getUserName(), 0) + 1);
                        opponentsPlayedAgainst.put(g.getPlayer3().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer3().getUserName(), 0) + 1);
                    }
                }
                else if(g.getPlayer3() == user){
                    playersPlayedWith.put(g.getPlayer1().getUserName(),
                            playersPlayedWith.getOrDefault(g.getPlayer1().getUserName(), 0) + 1);

                    if(g.getTeam1Score() < g.getTeam2Score()){
                        opponentsPlayedAgainst.put(g.getPlayer2().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer2().getUserName(), 0) + 1);
                        opponentsPlayedAgainst.put(g.getPlayer4().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer4().getUserName(), 0) + 1);
                    }
                }
                else{
                    playersPlayedWith.put(g.getPlayer2().getUserName(),
                            playersPlayedWith.getOrDefault(g.getPlayer2().getUserName(), 0) + 1);

                    if(g.getTeam1Score() > g.getTeam2Score()){
                        opponentsPlayedAgainst.put(g.getPlayer1().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer1().getUserName(), 0) + 1);
                        opponentsPlayedAgainst.put(g.getPlayer3().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer3().getUserName(), 0) + 1);
                    }
                }
            }
            // Use streams to get the maximum entry for each map
            Map.Entry<String, Integer> mostLostToOpponent = opponentsPlayedAgainst.entrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);

            Map.Entry<String, Integer> mostPlayedWithTeammate = playersPlayedWith.entrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);

            Map.Entry<Integer, Integer> mostFrequentLocation = locationsPlayedAt.entrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);

            // Assign proper variables for the maximum entries and check for null cases
            String playerMostPlayedWith = (mostPlayedWithTeammate != null) ? mostPlayedWithTeammate.getKey() : null;
            String opponentMostLostTo = (mostLostToOpponent != null) ? mostLostToOpponent.getKey() : null;
            Integer lossesToStrongestOpponent = (mostLostToOpponent != null) ? mostLostToOpponent.getValue() : null;
            Integer mostFrequentLocationID = (mostFrequentLocation != null) ? mostFrequentLocation.getKey() : null;

            // Make a new object of the DTO to send back with the right information
            UserStatisticsHistoricalDTO userStatsHst = new UserStatisticsHistoricalDTO(username,
                                                                                        t,
                                                                                        timestamp,
                                                                                        totalGames,
                                                                                        gamesWon,
                                                                                        gamesLost,
                                                                                        winLossRatio,
                                                                                        playerMostPlayedWith,
                                                                                        opponentMostLostTo,
                                                                                        lossesToStrongestOpponent,
                                                                                        mostFrequentLocationID);

            historicStatistics.add(userStatsHst);
        }


        return historicStatistics;
    }
}
