package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.LoginDTO;
import edu.missouristate.thepickleballcompanionapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository; // For accessing user data

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean validateUser(LoginDTO loginRequest) {
        String usernameOrEmail = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        User user = userRepository.getUserByIdOrEmail(usernameOrEmail);

        // Check if user exists and validate the password using BCryptPasswordEncoder
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false; // User not found or password doesn't match
    }
}
