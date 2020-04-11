package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import byu.cs.cs340.model.domain.Follow;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import byu.cs.cs340.server.lambda.handler.following.FollowUnfollowHandler;
import byu.cs.cs340.server.lambda.handler.following.FollowingHandler;
import byu.cs.cs340.model.domain.User;


public class FollowUnfollowHandlerTest {

    User user1 = new User("User", "1", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
    User user49 = new User("User", "49", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
    Follow follow1 = new Follow(user1, user49);
    FollowingHandler followingHandler = new FollowingHandler();

    @Test
    void ifIsFollowing() {
        IfFollowingRequest request = new IfFollowingRequest("@User0", "User50");
        IfFollowingResponse response = followingHandler.handleRequest(request, null);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.getFollowing());
    }

    @Test
    void FollowUnfollow() {
        FollowUnfollowHandler handler = new FollowUnfollowHandler();
        FollowUnfollowRequest request = new FollowUnfollowRequest(new Follow(user1, user49));
        request.setAuthkey("authkey");
        FollowUnfollowResponse response = handler.handleRequest(request, null);

        Assertions.assertTrue(response.isSuccess());
    }
}
