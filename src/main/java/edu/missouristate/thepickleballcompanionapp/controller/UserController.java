package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import edu.missouristate.thepickleballcompanionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private final UserRepository userRepo;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @RequestMapping(value = "/users/find", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findByUsername(@RequestParam("username") String username) {
        return userService.getUsersByUserName(username);
    }

}
