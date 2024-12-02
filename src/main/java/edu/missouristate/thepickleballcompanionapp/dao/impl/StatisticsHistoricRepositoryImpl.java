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

    public List<UserStatisticsHistoric> getHistoricStatistics(User selectedUser){
        return from(userStatisticsHistoric)
                .select(userStatisticsHistoric)
                .where(userStatisticsHistoric.username.eq(selectedUser.getUserName()))
                .orderBy(userStatisticsHistoric.maxTimestamp.desc())
                .fetch();
    }
}
