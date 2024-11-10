package edu.missouristate.thepickleballcompanionapp.dao.custom;

import edu.missouristate.thepickleballcompanionapp.domain.User;

import java.util.List;

public interface UserRepositoryCustom {
    public List<User> getAllUsers();

    public User getUserById(String username);
    public User getUserByIdOrEmail(String usernameOrEmail);
}
