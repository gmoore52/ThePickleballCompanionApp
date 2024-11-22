package edu.missouristate.thepickleballcompanionapp.domain.enums;

public enum FriendRequestStatus {
    ACC("ACC"), // Accepted
    REJ("REJ"), // Rejected
    REV("REV"), // Revoked
    REC("REC"); // Received

    private final String status;

    FriendRequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    // Additional helper methods can be added if needed.
}

