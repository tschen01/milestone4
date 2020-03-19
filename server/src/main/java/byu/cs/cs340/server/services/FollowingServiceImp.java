package byu.cs.cs340.server.services;


import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.FollowingService;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;

public class FollowingServiceImp implements FollowingService {


    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {
        return null;
    }

    @Override
    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) {
        return null;
    }

    @Override
    public Boolean following(User user) {
        return null;
    }

    @Override
    public FolloweeResponse getFollowers(FolloweeRequest request) {
        return null;
    }
}
