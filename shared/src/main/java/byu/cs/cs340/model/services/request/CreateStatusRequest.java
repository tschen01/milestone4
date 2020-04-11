package byu.cs.cs340.model.services.request;

import java.sql.Timestamp;

import byu.cs.cs340.model.domain.User;

public class CreateStatusRequest extends Request {
    private User user;
    private String message;
    private String timestamp;

    public CreateStatusRequest(){ }

    public  CreateStatusRequest(User user, String message, String timestamp) {
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
