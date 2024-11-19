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
                                    Math.toIntExact(stats.getMostFreqLocation().getId()),
                                    stats.getMostFreqTeamate().getUserName(),
                                    stats.getStrongestOpponent().getUserName(),
                                    stats.getMostLossesToStrongestOpponent());
        }
        return null;
    }

    @Override
    public List<UserStatisticsHistoricalDTO> getUserStatisticsHistoric(List<String> username, String stat) {
        // Make variables for the repository methods and timestamp for when the request was received
        String name = "null";
        List<UserStatisticsHistoricalDTO> userStatisticsHistoricalDTOList = new ArrayList<>();
        if(Arrays.asList("totalWins", "totalLosses", "totalGames", "winLossRatio",
                "mostFreqLocation", "mostFreqTeammate", "strongestOpponent",
                "mostLossesToStrongestOpponent").contains(stat)){
            name = stat;
            stat = "get" + stat.substring(0, 1).toUpperCase() + stat.substring(1);
        }
        else{
            return null;
        }
        for(String uname : username){
            List<AbstractMap.SimpleEntry<Timestamp, Number>> series = new ArrayList<>();
            User user = userRepository.getUserById(uname);
            List<UserStatisticsHistoric> userStatsHst = statisticsHistoricRepository.getHistoricStatistics(user);
            for(UserStatisticsHistoric historic : userStatsHst){
                try{
                    Number val = (Number)historic.getClass().getDeclaredMethod(stat).invoke(historic);
                    Timestamp timestamp = historic.getMaxTimestamp();
                    series.add(new AbstractMap.SimpleEntry<>(timestamp, val));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            userStatisticsHistoricalDTOList.add(new UserStatisticsHistoricalDTO(name, series));
        }
        return userStatisticsHistoricalDTOList;
    }

    @Override
    public boolean stageUserStatisticsHistoric(List<String> username) {
        boolean complete = false;
        // Make variables for the repository methods and timestamp for when the request was received
        for(String name : username){
            User user = userRepository.getUserById(name);
            List<UserStatisticsHistoric> userStatsHst = statisticsHistoricRepository.getHistoricStatistics(user);
            statisticsHistoricRepository.saveAll(userStatsHst);

        }
        complete = true;
        return complete;
    }
}
