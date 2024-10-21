package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import edu.missouristate.thepickleballcompanionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")  // Allows only localhost:3000
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO findByUsername(@RequestParam("username") String username) {
        return userService.getUserByUserName(username);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }
}
