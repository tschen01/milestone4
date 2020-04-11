package byu.cs.cs340.model.services.request;

import byu.cs.cs340.model.domain.User;

public class FolloweeRequest extends Request {
    private User followee;
    private int limit;
    private String lastFollower;

    public FolloweeRequest() {}

    public FolloweeRequest(User follower, int limit, String lastFollowee) {
        this.followee = follower;
        this.limit = limit;
        this.lastFollower = lastFollowee;
    }

    public User getFollowee() {
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

    public String getLastFollower() {
        return lastFollower;
    }

    public void setLastFollower(String lastFollower) {
        this.lastFollower = lastFollower;
    }
}
