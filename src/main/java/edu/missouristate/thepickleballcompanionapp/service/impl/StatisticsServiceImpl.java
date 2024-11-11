package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.GameRepository;
import edu.missouristate.thepickleballcompanionapp.dao.StatisticsRepository;
import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.UserStatistics;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsDTO;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsHistoricalDTO;
import edu.missouristate.thepickleballcompanionapp.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserStatisticsDTO getUserStatistics(String username) {
        UserStatistics stats = statisticsRepository.getUserStatisticsByUsername(username);
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

    @Override
    public List<UserStatisticsHistoricalDTO> getUserStatisticsHistoric(String username) {
        // Make variables for the repository methods and timestamp for when the request was received
        User user = userRepository.getUserById(username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return statisticsRepository.getHistoricStatistics(timestamp, user);
    }
}
