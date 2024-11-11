package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.RequestRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.dao.custom.StatisticsRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Request;
import edu.missouristate.thepickleballcompanionapp.domain.UserStatistics;
import org.springframework.data.repository.CrudRepository;

public interface StatisticsRepository extends CrudRepository<UserStatistics, Integer>, StatisticsRepositoryCustom {
}
