package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.service.impl.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/friends")
public class RequestController {

    @Autowired
    private RequestServiceImpl requestService;

    // get the friendship status between two users
    @GetMapping("/status/{selectedUsername}/{loggedInUsername}")
    public ResponseEntity<String> getFriendStatus(
            @PathVariable("selectedUsername") String selectedUsername,
            @PathVariable("loggedInUsername") String loggedInUsername) {

        String status = requestService.getFriendStatusForSelectedUser(selectedUsername, loggedInUsername);
        return ResponseEntity.ok(status);
    }

    // create a new friend request
    @PostMapping("/request")
    public ResponseEntity<String> createFriendRequest(@RequestParam("toUsername") String toUsername,
                                                      @RequestParam("fromUsername") String fromUsername) {
        try {
            requestService.createFriendRequest(toUsername, fromUsername);
            return ResponseEntity.status(HttpStatus.CREATED).body("Friend request sent successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // accept a friend request
    @PutMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam("toUsername") String toUsername,
                                                      @RequestParam("fromUsername") String fromUsername) {
        try {
            requestService.acceptFriendRequest(toUsername, fromUsername);
            return ResponseEntity.ok("Friend request accepted.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // reject a friend request
    @PutMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam("toUsername") String toUsername,
                                                      @RequestParam("fromUsername") String fromUsername) {
        try {
            requestService.rejectFriendRequest(toUsername, fromUsername);
            return ResponseEntity.ok("Friend request rejected.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // revoke a friend request
    @PutMapping("/revoke")
    public ResponseEntity<String> revokeFriendRequest(@RequestParam("toUsername") String toUsername,
                                                      @RequestParam("fromUsername") String fromUsername) {
        try {
            requestService.revokeFriendRequest(toUsername, fromUsername);
            return ResponseEntity.ok("Friend request revoked.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // get all friends for a selected user
    @GetMapping("/friends/{username}")
    public ResponseEntity<List<User>> getFriendsForSelectedUser(@PathVariable("username") String username) {
        List<User> friends = requestService.getFriendsForSelectedUser(username);
        return ResponseEntity.ok(friends);
    }
}
