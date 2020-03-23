package byu.cs.cs340.model.services.request;

import byu.cs.cs340.model.domain.User;

public class IfFollowingRequest {

    User user;

    public IfFollowingRequest() {}

    public IfFollowingRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
