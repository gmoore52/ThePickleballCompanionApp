package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.getAllUsers();
    }
}
