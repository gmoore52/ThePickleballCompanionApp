package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.GameRepository;
import edu.missouristate.thepickleballcompanionapp.dao.StatisticsHistoricRepository;
import edu.missouristate.thepickleballcompanionapp.dao.StatisticsRepository;
import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.UserStatistics;
import edu.missouristate.thepickleballcompanionapp.domain.UserStatisticsHistoric;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsDTO;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsHistoricalDTO;
import edu.missouristate.thepickleballcompanionapp.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private StatisticsHistoricRepository statisticsHistoricRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserStatisticsDTO getUserStatistics(String username) {
        UserStatistics stats = statisticsRepository.getUserStatisticsByUsername(username);
        if(stats != null){
        return new UserStatisticsDTO(stats.getUserName(),
                                    stats.getTotalGames(),
                                    stats.getTotalWins(),
                                    stats.getTotalLosses(),
                                    (float)stats.getWinLossRatio(),
                                    stats.getMostFreqLocation() != null ?
                                            Math.toIntExact(stats.getMostFreqLocation().getId()) :
                                            -1,
                                    stats.getMostFreqTeamate() != null ?
                                            stats.getMostFreqTeamate().getUserName() :
                                            "Not enough games played",
                                    stats.getStrongestOpponent() != null ?
                                            stats.getStrongestOpponent().getUserName() :
                                            "No strongest opponent",
                                    stats.getMostLossesToStrongestOpponent());
        }
        return null;
    }

    @Override
    public List<UserStatisticsHistoricalDTO> getUserStatisticsHistoric(List<String> username, String stat) {
        // Make variables for the repository methods and timestamp for when the request was received
        String name = "null";
//        System.out.println(username + " " + stat);
        List<UserStatisticsHistoricalDTO> userStatisticsHistoricalDTOList = new ArrayList<>();
        if(Arrays.asList("totalWins", "totalLosses", "totalGames", "winLossRatio",
                "mostFreqLocation", "mostFreqTeammate", "strongestOpponent",
                "mostLossesToStrongestOpponent").contains(stat)){
            name = stat;
            name = (stat.substring(0, 1).toUpperCase() + stat.substring(1)).replaceAll("([A-Z][a-z]+)", " $1") // Words beginning with UC
                    .replaceAll("([A-Z][A-Z]+)", " $1") // "Words" of only UC
                    .replaceAll("([^A-Za-z ]+)", " $1") // "Words" of non-letters
                    .trim();
            stat = "get" + stat.substring(0, 1).toUpperCase() + stat.substring(1);
        }
        else{
            return null;
        }
        for(String uname : username){
            List<List<Number>> series = new ArrayList<>();
            User user = userRepository.getUserById(uname);
            List<UserStatisticsHistoric> userStatsHst = statisticsHistoricRepository.getHistoricStatistics(user);
            for(UserStatisticsHistoric historic : userStatsHst){
                try{
                    Number val = (Number)historic.getClass().getDeclaredMethod(stat).invoke(historic);
                    Timestamp timestamp = historic.getMaxTimestamp();
                    series.add(Arrays.asList(timestamp.getTime(), val));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            userStatisticsHistoricalDTOList.add(new UserStatisticsHistoricalDTO(name + " - " + uname, series));
        }
        return userStatisticsHistoricalDTOList;
    }

    @Override
    public boolean stageUserStatisticsHistoric(List<String> username) {
        boolean complete = false;
        // Make variables for the repository methods and timestamp for when the request was received
        for(String name : username){
            User user = userRepository.getUserById(name);
            List<UserStatisticsHistoric> userStatsHst = statisticsHistoricRepository.getHistoricStatisticsForStage(user);
            statisticsHistoricRepository.saveAll(userStatsHst);

        }
        complete = true;
        return complete;
    }

    @Override
    public boolean stageAllStatisticsHistoric() {
        List<String> usernames = userRepository.findAll().stream().map(u -> u.getUserName()).collect(Collectors.toList());
        return stageUserStatisticsHistoric(usernames);
    }
}
