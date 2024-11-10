package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.dto.LoginDTO;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import edu.missouristate.thepickleballcompanionapp.service.LoginService;
import edu.missouristate.thepickleballcompanionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginRequest) {
        // Call the service method to validate the user credentials
        boolean isValidUser = loginService.validateUser(loginRequest);
        if (isValidUser) {
            UserDTO userDTO = userService.getUserByUserName(loginRequest.getUsername());
            return ResponseEntity.ok(userDTO); // Send back a success message
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Send back an error message
        }
    }
}
