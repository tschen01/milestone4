package edu.byu.cs.tweeter.model.services;


import java.io.IOException;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.FollowingService;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import edu.byu.cs.tweeter.net.ServerFacade;

public class FollowingServiceProxy implements FollowingService {
    private static final String URL_PATH_FOLLOWING = "/getfollowing";
    private static final String URL_PATH_FOLLOWER = "/getfollower";
    private static final String URL_PATH_FOLLOWUNFOLLOW = "/followunfollow";
    private static final String URL_PATH_FOLLOW = "/follow";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        return serverFacade.getFollowees(request, URL_PATH_FOLLOWING);
    }

    @Override
    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) throws IOException {
        FollowUnfollowResponse response = serverFacade.followUnfollow(request, URL_PATH_FOLLOWUNFOLLOW);
        return response;
    }

    @Override
    public IfFollowingResponse following(IfFollowingRequest user) throws IOException {
        return serverFacade.following(user, URL_PATH_FOLLOW);
    }

    @Override
    public FolloweeResponse getFollowers(FolloweeRequest request) throws IOException {
        return serverFacade.getFollowers(request, URL_PATH_FOLLOWER);
    }
}
