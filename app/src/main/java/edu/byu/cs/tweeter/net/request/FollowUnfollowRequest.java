package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Follow;

public class FollowUnfollowRequest {

    Follow follow;

    public FollowUnfollowRequest(Follow follow) {
        this.follow = follow;
    }

    public Follow getFollow() {
        return follow;
    }
}
