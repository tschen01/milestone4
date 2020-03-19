package byu.cs.cs340.model.services.request;

import byu.cs.cs340.model.domain.User;

public class FollowingRequest {

    private final User follower;
    private final int limit;
    private final User lastFollowee;

    public FollowingRequest(User follower, int limit, User lastFollowee) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    public Object getFollower() {
        return follower;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollowee() {
        return lastFollowee;
    }
}
