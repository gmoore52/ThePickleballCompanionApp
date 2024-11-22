package edu.missouristate.thepickleballcompanionapp.service;

import edu.missouristate.thepickleballcompanionapp.domain.Request;
import edu.missouristate.thepickleballcompanionapp.domain.User;

import java.util.List;

public interface RequestService {
    public String getFriendStatusForSelectedUser(String selectedUsername, String loggedInUsername);
    public void createFriendRequest(String toUsername, String fromUsername);
    public void acceptFriendRequest(String toUsername, String fromUsername);
    public void rejectFriendRequest(String toUsername, String fromUsername);
    public void revokeFriendRequest(String toUsername, String fromUsername);
    public List<User> getFriendsForSelectedUser(String username);
}
