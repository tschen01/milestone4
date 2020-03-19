package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpResponse extends Response {

    private User user;

    public SignUpResponse(boolean success) {
        super(success);
    }
    public SignUpResponse(boolean success, User user) {
        super(success);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
