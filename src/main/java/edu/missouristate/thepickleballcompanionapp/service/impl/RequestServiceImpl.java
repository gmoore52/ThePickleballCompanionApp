package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.RequestRepository;
import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.Request;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.enums.FriendRequestStatus;
import edu.missouristate.thepickleballcompanionapp.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getFriendStatusForSelectedUser(String selectedUsername, String loggedInUsername) {
        User originUser = userRepository.findByUserName(selectedUsername);
        User destinationUser = userRepository.findByUserName(loggedInUsername);
        Request request = requestRepository.findByOriginAndDestination(originUser, destinationUser);

        if (request != null) {
            switch (request.getStatus()) {
                case "ACC":
                    return "You are friends";
                case "REV":
                    return "Friend request revoked";
                case "REC":
                    return "Friend request received";
                case "REJ":
                    return "Friend request rejected";
            }
        }
        // no request exists
        return "No request";
    }

    @Override
    public void createFriendRequest(String toUsername, String fromUsername) {
        User originUser = userRepository.findByUserName(toUsername);
        User destinationUser = userRepository.findByUserName(fromUsername);
        if (requestRepository.existsByOriginAndDestination(originUser, destinationUser)) {
            throw new IllegalStateException("Friend request already exists.");
        }

        // Create a new Request object and manually set its fields
        Request newRequest = new Request();
        newRequest.setOrigin(originUser); // Assuming a method to find a user by username
        newRequest.setDestination(destinationUser); // Assuming a method to find a user by username
        newRequest.setStatus(String.valueOf(FriendRequestStatus.REC)); // "REC" status for a received friend request
        newRequest.setRequestDate(new Timestamp(System.currentTimeMillis())); // Set the current timestamp for the request date

        // Save the new request to the repository
        requestRepository.save(newRequest);
    }


    @Override
    public void acceptFriendRequest(String toUsername, String fromUsername) {
        User originUser = userRepository.findByUserName(toUsername);
        User destinationUser = userRepository.findByUserName(fromUsername);
        Request request = requestRepository.findByOriginAndDestination(originUser, destinationUser);

        if (request != null && request.getStatus().equals(String.valueOf(FriendRequestStatus.REC))) {
            // Update the status to accepted
            request.setStatus(String.valueOf(FriendRequestStatus.ACC));
            requestRepository.save(request);

        } else {
            throw new IllegalStateException("No pending request to accept.");
        }
    }

    @Override
    public void rejectFriendRequest(String toUsername, String fromUsername) {
        User originUser = userRepository.findByUserName(toUsername);
        User destinationUser = userRepository.findByUserName(fromUsername);
        // Find the pending request
        Request request = requestRepository.findByOriginAndDestination(originUser, destinationUser);

        if (request != null && request.getStatus().equals(String.valueOf(FriendRequestStatus.REC))) {
            // Update the status to rejected
            request.setStatus(String.valueOf(FriendRequestStatus.REJ));
            requestRepository.save(request);
        } else {
            throw new IllegalStateException("No pending request to reject.");
        }
    }

    public void revokeFriendRequest(String toUsername, String fromUsername) {
        User originUser = userRepository.findByUserName(toUsername);
        User destinationUser = userRepository.findByUserName(fromUsername);
        Request request = requestRepository.findByOriginAndDestination(originUser, destinationUser);

        if (request != null && request.getStatus().equals(String.valueOf(FriendRequestStatus.REC))) {
            request.setStatus(String.valueOf(FriendRequestStatus.REV));
            requestRepository.save(request);
        } else {
            throw new IllegalStateException("No sent request to revoke.");
        }
    }

    // get friends for the selected user by checking requests with status "ACC"
    public List<User> getFriendsForSelectedUser(String username) {
        User user = userRepository.findByUserName(username);
        List<Request> requests = requestRepository.findByStatusAndOriginOrDestination("ACC", user);
        List<User> friends = new ArrayList<>();
        for (Request request : requests) {
            if (request.getOrigin().getUserName().equals(username)) {
                friends.add(request.getDestination());
            } else {
                friends.add(request.getOrigin());
            }
        }

        return friends;
    }
}
