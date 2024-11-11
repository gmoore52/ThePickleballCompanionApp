package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import edu.missouristate.thepickleballcompanionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Initialize BCryptPasswordEncoder for password encoding
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
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
    public UserDTO getUserByUserName(String username) {
        User user = userRepository.findByUserName(username);
        UserDTO userDTO = new UserDTO(
                user.getUserName(),
                user.getUserFullName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getProfileImgLoc(),
                user.getSkillLevel(),
                user.getAccCreationDate()
        );
        return userDTO;
    }

    @Override
    public Boolean addUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setUserFullName(userDTO.getUserFullName());
        user.setEmailAddress(userDTO.getEmailAddress());
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setProfileImgLoc(userDTO.getProfileImgLoc());
        user.setSkillLevel(userDTO.getSkillLevel());
        user.setAccCreationDate(userDTO.getAccCreationDate());

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
