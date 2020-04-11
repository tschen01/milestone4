package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import byu.cs.cs340.server.lambda.handler.following.FollowingHandler;


public class FollowUnfollowHandlerTest {
    FollowingHandler followingHandler = new FollowingHandler();

    @Test
    void ifIsFollowing() {
        IfFollowingRequest request = new IfFollowingRequest("@User0", "User50");
        IfFollowingResponse response = followingHandler.handleRequest(request, null);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.getFollowing());
    }
}
