package byu.cs.cs340.model.services;


import java.io.IOException;

import javax.xml.ws.Response;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;

public interface FollowingService {

    public FollowingResponse getFollowees(FollowingRequest request) throws IOException;

    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) throws IOException;

    public IfFollowingResponse following(IfFollowingRequest request) throws IOException;

    public FolloweeResponse getFollowers(FolloweeRequest request) throws IOException;
}
