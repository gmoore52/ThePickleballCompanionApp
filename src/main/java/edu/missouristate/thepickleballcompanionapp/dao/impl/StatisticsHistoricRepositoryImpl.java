package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.StatisticsHistoricRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.dao.custom.StatisticsRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.*;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsHistoricalDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
//import org.hibernate.

import java.sql.Timestamp;
import java.util.*;

@Repository
public class StatisticsHistoricRepositoryImpl extends QuerydslRepositorySupport implements StatisticsHistoricRepositoryCustom {
//    QUserStatistics userStatistics = QUserStatistics.userStatistics;
    QUserStatisticsHistoric userStatisticsHistoric = QUserStatisticsHistoric.userStatisticsHistoric;
    QGame game = QGame.game;
    QUser user = QUser.user;
    QLocation location = QLocation.location;


    public StatisticsHistoricRepositoryImpl() {
        super(QUserStatisticsHistoric.class);
    }

    public List<UserStatisticsHistoric> getHistoricStatisticsForStage(User selectedUser){
        List<UserStatisticsHistoric> historicStatistics = new ArrayList<>();
        // Loop through each possible timestamp for a game to happen for this user
        for(Timestamp t : from(game)
                .select(game.gameDate).distinct()
                .where(game.player1.eq(selectedUser)
                        .or(game.player2.eq(selectedUser))
                        .or(game.player3.eq(selectedUser))
                        .or(game.player4.eq(selectedUser)))
                .fetch()){
            // Make a list of each game that is before or on the timestamp in this iteration
            List<Game> games = from(game)
                    .select(game)
                    .where(game.gameDate.loe(t)
                            .and(game.player1.eq(selectedUser)
                                    .or(game.player2.eq(selectedUser)
                                            .or(game.player3.eq(selectedUser)
                                                    .or(game.player4.eq(selectedUser))))))
                    .fetch();
            // Get dummy variables ready for generating the DTO
            String username = selectedUser.getUserName();
            int totalGames = games.size();
            long gamesWon = games.stream()
                    .filter(game -> {if(game.getPlayer1() == selectedUser || game.getPlayer3() == selectedUser){
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
                if(g.getPlayer1() == selectedUser){
                    playersPlayedWith.put(g.getPlayer3().getUserName(),
                            playersPlayedWith.getOrDefault(g.getPlayer3().getUserName(), 0) + 1);
                    if(g.getTeam1Score() < g.getTeam2Score()){
                        opponentsPlayedAgainst.put(g.getPlayer2().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer2().getUserName(), 0) + 1);
                        opponentsPlayedAgainst.put(g.getPlayer4().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer4().getUserName(), 0) + 1);
                    }
                }
                else if(g.getPlayer2() == selectedUser){
                    playersPlayedWith.put(g.getPlayer4().getUserName(),
                            playersPlayedWith.getOrDefault(g.getPlayer4().getUserName(), 0) + 1);
                    if(g.getTeam1Score() > g.getTeam2Score()){
                        opponentsPlayedAgainst.put(g.getPlayer1().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer1().getUserName(), 0) + 1);
                        opponentsPlayedAgainst.put(g.getPlayer3().getUserName(),
                                opponentsPlayedAgainst.getOrDefault(g.getPlayer3().getUserName(), 0) + 1);
                    }
                }
                else if(g.getPlayer3() == selectedUser){
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
            Map.Entry<Integer, Integer> mostFrequentLocationPair = locationsPlayedAt.entrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);
            // Assign proper variables for the maximum entries and check for null cases
            String playerMostPlayedWith = (mostPlayedWithTeammate != null) ? mostPlayedWithTeammate.getKey() : null;
            String opponentMostLostTo = (mostLostToOpponent != null) ? mostLostToOpponent.getKey() : null;
            Integer lossesToStrongestOpponent = (mostLostToOpponent != null) ? mostLostToOpponent.getValue() : Integer.valueOf(0);
            Integer mostFrequentLocationID = (mostFrequentLocationPair != null) ? mostFrequentLocationPair.getKey() : null;
            Location mostFrequentLocation = (mostFrequentLocationID != null) ? from(location)
                    .select(location)
                    .where(location.id.eq(Long.valueOf(mostFrequentLocationID)))
                    .fetchOne()
                    : null;
            User mostFreqTeammate = from(user)
                    .select(user)
                    .where(user.userName.eq(playerMostPlayedWith))
                    .fetchOne();
            User strongestOpponent = from(user)
                    .select(user)
                    .where(user.userName.eq(opponentMostLostTo))
                    .fetchOne();
            // Make a new object of the DTO to send back with the right information
            UserStatisticsHistoric userStatsHst = new UserStatisticsHistoric(username,
                    t,
                    Math.toIntExact(gamesWon),
                    Math.toIntExact(gamesLost),
                    totalGames,
                    winLossRatio,
                    mostFrequentLocation,
                    mostFreqTeammate,
                    strongestOpponent,
                    lossesToStrongestOpponent);
            historicStatistics.add(userStatsHst);
        }
        return historicStatistics;
    }

    public List<UserStatisticsHistoric> getHistoricStatistics(User selectedUser){
        return from(userStatisticsHistoric)
                .select(userStatisticsHistoric)
                .where(userStatisticsHistoric.username.eq(selectedUser.getUserName()))
                .orderBy(userStatisticsHistoric.maxTimestamp.desc())
                .fetch();
    }
}
