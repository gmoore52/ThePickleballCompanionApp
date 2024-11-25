package edu.missouristate.thepickleballcompanionapp.service.impl;

import edu.missouristate.thepickleballcompanionapp.dao.RequestRepository;
import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.Request;
import edu.missouristate.thepickleballcompanionapp.domain.RequestType;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
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
        User originUser = userRepository.findByUserName(loggedInUsername);
        User destinationUser = userRepository.findByUserName(selectedUsername);

        // Check for request from origin to destination
        Request requestFromOrigin = requestRepository.findByOriginAndDestination(originUser, destinationUser);

        // Check for request from destination to origin
        Request requestFromDestination = requestRepository.findByOriginAndDestination(destinationUser, originUser);

        // Check if both origin and destination have accepted the friend request
        if (requestFromOrigin != null && requestFromDestination != null) {
            if ("ACC".equals(requestFromOrigin.getStatus()) && "ACC".equals(requestFromDestination.getStatus())) {
                return "You are friends";
            }
        }

        if (requestFromOrigin != null) {
            switch (requestFromOrigin.getStatus()) {
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
        User originUser = userRepository.findByUserName(fromUsername);
        User destinationUser = userRepository.findByUserName(toUsername);
        if (requestRepository.existsByOriginAndDestination(originUser, destinationUser)) {
            throw new IllegalStateException("Friend request already exists.");
        }

        // Create a new Request object and manually set its fields
        Request newRequest = new Request();
        newRequest.setOrigin(originUser); // Assuming a method to find a user by username
        newRequest.setDestination(destinationUser); // Assuming a method to find a user by username
        newRequest.setStatus(String.valueOf(FriendRequestStatus.REC)); // "REC" status for a received friend request
        newRequest.setRequestDate(new Timestamp(System.currentTimeMillis())); // Set the current timestamp for the request date
        newRequest.setRequestType(RequestType.FRIEND_REQUEST);
        // Save the new request to the repository
        requestRepository.save(newRequest);
    }


    @Override
    public void acceptFriendRequest(String toUsername, String fromUsername) {
        User originUser = userRepository.findByUserName(fromUsername);
        User destinationUser = userRepository.findByUserName(toUsername);
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
        User originUser = userRepository.findByUserName(fromUsername);
        User destinationUser = userRepository.findByUserName(toUsername);
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
        User originUser = userRepository.findByUserName(fromUsername);
        User destinationUser = userRepository.findByUserName(toUsername);
        Request request = requestRepository.findByOriginAndDestination(originUser, destinationUser);

        if (request != null && request.getStatus().equals(String.valueOf(FriendRequestStatus.REC))) {
            request.setStatus(String.valueOf(FriendRequestStatus.REV));
            requestRepository.save(request);
        } else {
            throw new IllegalStateException("No sent request to revoke.");
        }
    }

    // get friends for the selected user by checking requests with status "ACC"
    public List<UserDTO> getFriendsForSelectedUser(String username) {
        User user = userRepository.findByUserName(username);
        List<Request> requests = requestRepository.findByStatusAndOriginOrDestination("ACC", user);
        List<UserDTO> friends = new ArrayList<>();

        for (Request request : requests) {
            User friend;
            if (request.getOrigin().getUserName().equals(username)) {
                friend = request.getDestination();
            } else {
                friend = request.getOrigin();
            }

            // Check if the friend's username already exists in the list
            boolean exists = friends.stream()
                    .anyMatch(dto -> dto.getUserName().equals(friend.getUserName()));
            if (!exists) {
                // Map User to UserDTO
                UserDTO friendDTO = new UserDTO(
                        friend.getUserName(),
                        friend.getUserFullName(),
                        friend.getEmailAddress(),
                        friend.getPassword(),
                        friend.getProfileImgLoc(),
                        friend.getSkillLevel(),
                        friend.getAccCreationDate()
                );
                friends.add(friendDTO);
            }
        }

        return friends;
    }

    // get friends for the selected user by checking requests with status "REC"
    public List<UserDTO> getFriendRequestsForSelectedUser(String username) {
        User user = userRepository.findByUserName(username);
        List<Request> requests = requestRepository.findByStatusOriginOnly("REC", user);
        List<UserDTO> friendRequests = new ArrayList<>();

        for (Request request : requests) {
            User friend;
            if (request.getOrigin().getUserName().equals(username)) {
                friend = request.getDestination();
            } else {
                friend = request.getOrigin();
            }

            // Check if the friend's username already exists in the list
            boolean exists = friendRequests.stream()
                    .anyMatch(dto -> dto.getUserName().equals(friend.getUserName()));
            if (!exists) {
                // Map User to UserDTO
                UserDTO friendDTO = new UserDTO(
                        friend.getUserName(),
                        friend.getUserFullName(),
                        friend.getEmailAddress(),
                        friend.getPassword(),
                        friend.getProfileImgLoc(),
                        friend.getSkillLevel(),
                        friend.getAccCreationDate()
                );
                friendRequests.add(friendDTO);
            }
        }

        return friendRequests;
    }
}
