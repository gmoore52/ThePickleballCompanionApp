package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import edu.missouristate.thepickleballcompanionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
    public Boolean checkUserNameAlreadyExists(String username) {
        try {
            // Check if the username exists in the database
            return userRepository.existsByUserName(username); // Returns true if username exists
        } catch (Exception e) {
            // Log the error if something goes wrong (e.g., database issues)
            e.printStackTrace();
            return true; // Return true if there was an error, assuming the username might exist
        }
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

    public Boolean uploadProfileImage(String username, String imageData){
        try {
            // Get the original filename
            String filename = "./PickleBallCompanionAppUI/public/images/" + username + ".jpg";

            byte[] imageBytes = Base64.getDecoder().decode(imageData);

            // Create the file path where the image will be saved
            File destinationFile = new File("" + filename);

            try (FileOutputStream fos = new FileOutputStream(destinationFile)) {
                fos.write(imageBytes);
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }
}
