package byu.cs.cs340.model.services.request;

import java.sql.Timestamp;

import byu.cs.cs340.model.domain.User;

public class CreateStatusRequest {
    private User user;
    private String message;
    private Timestamp timestamp;

    public  CreateStatusRequest(User user, String message, Timestamp timestamp) {
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
