package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FolloweeRequest {
    private final User followee;
    private final int limit;
    private final User lastFollower;

    public FolloweeRequest(User follower, int limit, User lastFollowee) {
        this.followee = follower;
        this.limit = limit;
        this.lastFollower = lastFollowee;
    }

    public User getFollower() {
        return followee;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollower() {
        return lastFollower;
    }

}
