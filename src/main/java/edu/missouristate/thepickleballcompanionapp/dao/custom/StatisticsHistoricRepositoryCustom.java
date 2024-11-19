package edu.missouristate.thepickleballcompanionapp.dao.custom;

import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.UserStatistics;
import edu.missouristate.thepickleballcompanionapp.domain.UserStatisticsHistoric;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsHistoricalDTO;

import java.sql.Timestamp;
import java.util.List;

public interface StatisticsHistoricRepositoryCustom {
//    public UserStatistics getUserStatisticsByUsername(String username);
    public List<UserStatisticsHistoric> getHistoricStatistics(User user);
}
