package edu.missouristate.thepickleballcompanionapp.service;

import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsDTO;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsHistoricalDTO;

import java.util.List;

public interface StatisticsService {
    UserStatisticsDTO getUserStatistics(String username);
    List<UserStatisticsHistoricalDTO> getUserStatisticsHistoric(List<String> username, String stat);
    boolean stageUserStatisticsHistoric(List<String> username);
}
