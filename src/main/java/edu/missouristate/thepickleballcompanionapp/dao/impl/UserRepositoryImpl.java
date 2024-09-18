package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.UserRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.QUser;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    QUser user = QUser.user;


    public UserRepositoryImpl() {
        super(QUser.class);
    }
}
