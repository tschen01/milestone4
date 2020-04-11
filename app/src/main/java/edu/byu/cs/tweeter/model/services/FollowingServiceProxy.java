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
    private static final String URL_PATH_FOLLOWING = "/following";
    private static final String URL_PATH_FOLLOWER = "/getfollowers";
    private static final String URL_PATH_FOLLOWUNFOLLOW = "/followuser";
    private static final String URL_PATH_FOLLOW = "/isfollowing";

    private final ServerFacade serverFacade = new ServerFacade();
    private String lastFollowee;
    private String lastFollower;

    @Override
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        request.setAuthkey(LoginServiceProxy.getInstance().getAuthToken());
        FollowingResponse response = serverFacade.getFollowees(request, URL_PATH_FOLLOWING);
        lastFollowee = response.getLastObject();
        return response;
    }

    @Override
    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) throws IOException {
        request.setAuthkey(LoginServiceProxy.getInstance().getAuthToken());
        return serverFacade.followUnfollow(request, URL_PATH_FOLLOWUNFOLLOW);
    }

    @Override
    public IfFollowingResponse following(IfFollowingRequest user) throws IOException {
        return serverFacade.following(user, URL_PATH_FOLLOW);
    }

    @Override
    public FolloweeResponse getFollowers(FolloweeRequest request) throws IOException {
        request.setAuthkey(LoginServiceProxy.getInstance().getAuthToken());
        FolloweeResponse response = serverFacade.getFollowers(request, URL_PATH_FOLLOWER);
        lastFollowee = response.getLastObject();
        return response;
    }
}
