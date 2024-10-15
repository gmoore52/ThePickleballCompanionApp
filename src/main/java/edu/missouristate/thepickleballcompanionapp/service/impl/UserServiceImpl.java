package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.LocationRepository;
import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import edu.missouristate.thepickleballcompanionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user: users){
            UserDTO userDTO = new UserDTO(
                    user.getUserName(),
                    user.getUserFullName(),
                    user.getEmailAddress(),
                    user.getPassword(),
                    user.getProfileImgLoc(),
                    user.getSkillLevel(),
                    user.getAccCreationDate()
            );

            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public List<UserDTO> getUsersByUserName(String username) {
        List<User> users = userRepository.findByUserName(username);

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user: users){
            UserDTO userDTO = new UserDTO(
                    user.getUserName(),
                    user.getUserFullName(),
                    user.getEmailAddress(),
                    user.getPassword(),
                    user.getProfileImgLoc(),
                    user.getSkillLevel(),
                    user.getAccCreationDate()
            );

            userDTOs.add(userDTO);
        }
        return userDTOs;
    }
}
