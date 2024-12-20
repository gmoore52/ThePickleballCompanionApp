package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import edu.missouristate.thepickleballcompanionapp.domain.dto.ProfileImageDTO;
import edu.missouristate.thepickleballcompanionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allows only localhost:3000
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO findByUsername(@RequestParam("username") String username) {
        return userService.getUserByUserName(username);
    }

    @RequestMapping(value = "/check/username", method = RequestMethod.GET)
    @ResponseBody
    public Boolean checkUserNameAlreadyExists(@RequestParam("username") String username) {
        return userService.checkUserNameAlreadyExists(username);
    }

    @RequestMapping(value = "/uploadProfileImage", method = RequestMethod.POST)
    @ResponseBody
    public Boolean uploadProfileImage(@RequestBody ProfileImageDTO file) {
        return userService.uploadProfileImage(file.getUsername(), file.getImage());
    }

    @RequestMapping(value = "/all/users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }
}
