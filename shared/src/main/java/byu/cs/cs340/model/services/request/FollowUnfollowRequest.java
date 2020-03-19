package byu.cs.cs340.model.services.request;


import byu.cs.cs340.model.domain.Follow;

public class FollowUnfollowRequest {

    Follow follow;

    public FollowUnfollowRequest(Follow follow) {
        this.follow = follow;
    }

    public Follow getFollow() {
        return follow;
    }
}
