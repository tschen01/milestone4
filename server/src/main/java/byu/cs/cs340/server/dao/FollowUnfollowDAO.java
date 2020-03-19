package byu.cs.cs340.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.cs.cs340.model.domain.Follow;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;

public class FollowUnfollowDAO {
    List<User> follower = getFollowers();

    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) {
        //  no working for now
        return new FollowUnfollowResponse(true, true);
    }

    public Boolean following(User user) {
        return follower.contains(user);
    }

    private List<User> getFollowers() {
        return UserGenerator.getInstance().generateUsers(20);
    }

}
