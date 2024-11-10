package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.UserRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.QUser;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    QUser user = QUser.user;


    public UserRepositoryImpl() {
        super(QUser.class);
    }

    public List<User> getAllUsers() {
        return from(user)
                .fetch();
    }

    public User getUserById(String username){
        return from(user)
                .select(user)
                .where(user.userName.eq(username))
                .fetchFirst();
    }

    @Override
    public User getUserByIdOrEmail(String usernameOrEmail) {
        return from(user)
                .select(user)
                .where(user.userName.eq(usernameOrEmail).or(user.emailAddress.eq(usernameOrEmail)))
                .fetchFirst();
    }
}
