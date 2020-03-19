package byu.cs.cs340.model.services.response;

import byu.cs.cs340.model.domain.User;

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
