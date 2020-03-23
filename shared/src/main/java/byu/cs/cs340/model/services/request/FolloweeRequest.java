package byu.cs.cs340.model.services.request;

import byu.cs.cs340.model.domain.User;

public class FolloweeRequest {
    private User followee;
    private int limit;
    private User lastFollower;

    public FolloweeRequest() {}

    public FolloweeRequest(User follower, int limit, User lastFollowee) {
        this.followee = follower;
        this.limit = limit;
        this.lastFollower = lastFollowee;
    }

    public Object getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public User getLastFollower() {
        return lastFollower;
    }

    public void setLastFollower(User lastFollower) {
        this.lastFollower = lastFollower;
    }
}
