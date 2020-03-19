package byu.cs.cs340.model.services;


import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;

public interface FollowingService {

    public FollowingResponse getFollowees(FollowingRequest request);

    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request);

    public Boolean following(User user);

    public FolloweeResponse getFollowers(FolloweeRequest request);
}
