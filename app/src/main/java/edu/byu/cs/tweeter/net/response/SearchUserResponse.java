package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SearchUserResponse extends Response {
    private User user;

    public SearchUserResponse(boolean success) {
        super(success);
    }

    public SearchUserResponse(boolean success, User user) {
        super(success);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
