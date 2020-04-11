package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import byu.cs.cs340.model.domain.Follow;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.server.lambda.handler.following.GetFollowerHandler;


class FolloweeHandlerTest {

    private FollowerDAO followeeDAOSpy = new FollowerDAO();
    private User user = new User("Test ", "User", "@TestUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {

        FolloweeRequest request = new FolloweeRequest(user, 10, null);
        request.setAuthkey("authkey");
        FolloweeResponse response = followeeDAOSpy.getFollowers(request);

        Assertions.assertEquals(10, response.getFollowers().size());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertNotNull(response.getLastObject());
    }
    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {

        FolloweeRequest request = new FolloweeRequest(user, 10, null);
        request.setAuthkey("authkey");
        FolloweeResponse response = followeeDAOSpy.getFollowers(request);

        // Verify first page
        Assertions.assertEquals(10, response.getFollowers().size());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertNotNull(response.getLastObject());

        String last = response.getLastObject();

        // Get and verify second page
        request = new FolloweeRequest(user, 10, last);
        response = followeeDAOSpy.getFollowers(request);

        String seoncd = response.getLastObject();

        Assertions.assertEquals(10, response.getFollowers().size());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(seoncd, response.getLastObject());
        Assertions.assertNotEquals(last, response.getLastObject());

    }

}
