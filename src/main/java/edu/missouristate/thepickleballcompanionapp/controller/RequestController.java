package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
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
    @PostMapping("/create/{toUsername}/{fromUsername}")
    public ResponseEntity<String> createFriendRequest(@PathVariable("toUsername") String toUsername,
                                                      @PathVariable("fromUsername") String fromUsername) {
        try {
            requestService.createFriendRequest(toUsername, fromUsername);
            return ResponseEntity.status(HttpStatus.CREATED).body("Friend request sent successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // accept a friend request
    @PostMapping("/accept/{toUsername}/{fromUsername}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable("toUsername") String toUsername,
            @PathVariable("fromUsername") String fromUsername) {
        try {
            requestService.acceptFriendRequest(toUsername, fromUsername);
            return ResponseEntity.ok("Friend request accepted.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // reject a friend request
    @PostMapping("/reject/{toUsername}/{fromUsername}")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable("toUsername") String toUsername,
            @PathVariable("fromUsername") String fromUsername) {
        try {
            requestService.rejectFriendRequest(toUsername, fromUsername);
            return ResponseEntity.ok("Friend request rejected.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // revoke a friend request
    @PostMapping("/revoke/{toUsername}/{fromUsername}")
    public ResponseEntity<String> revokeFriendRequest(@PathVariable String toUsername,
            @PathVariable String fromUsername) {
        try {
            requestService.revokeFriendRequest(toUsername, fromUsername);
            return ResponseEntity.ok("Friend request revoked.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // get all friends for a selected user
    @GetMapping("/getFriends/{username}")
    public ResponseEntity<List<UserDTO>> getFriendsForSelectedUser(@PathVariable("username") String username) {
        List<UserDTO> friends = requestService.getFriendsForSelectedUser(username);
        return ResponseEntity.ok(friends);
    }

    // get all friend requests for a selected user
    @GetMapping("/getFriendRequests/{username}")
    public ResponseEntity<List<UserDTO>> getFriendRequestsForSelectedUser(@PathVariable("username") String username) {
        List<UserDTO> friends = requestService.getFriendRequestsForSelectedUser(username);
        return ResponseEntity.ok(friends);
    }
}
