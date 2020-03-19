package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class LoginResponse extends Response {

    private User user;

    public  LoginResponse(boolean success) {
        super(success);
    }
    public  LoginResponse(boolean success, User user) {
        super(success);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
