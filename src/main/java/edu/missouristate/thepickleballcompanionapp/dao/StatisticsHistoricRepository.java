package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.StatisticsHistoricRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.dao.custom.StatisticsRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.UserStatistics;
import edu.missouristate.thepickleballcompanionapp.domain.UserStatisticsHistoric;
import org.springframework.data.repository.CrudRepository;

public interface StatisticsHistoricRepository extends CrudRepository<UserStatisticsHistoric, Integer>, StatisticsHistoricRepositoryCustom {
//    public UserStatisticsHistoric save(UserStatisticsHistoric userStatisticsHistoric);
}
