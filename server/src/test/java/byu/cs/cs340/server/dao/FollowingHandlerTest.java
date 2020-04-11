package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import byu.cs.cs340.model.domain.Follow;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.server.lambda.handler.following.GetFollowingHandler;


class FollowingHandlerTest {


    private FollowingDAO followingDAOSpy = new FollowingDAO();
    private User user = new User("Test ", "User", "@TestUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {

        FollowingRequest request = new FollowingRequest(user, 5, null);
        FollowingResponse response = followingDAOSpy.getFollowees(request);

        Assertions.assertEquals(5, response.getFollowees().size());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertNotNull(response.getLastObject());
    }

    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {

        FollowingRequest request = new FollowingRequest(user, 10, null);
        FollowingResponse response = followingDAOSpy.getFollowees(request);

        String last = response.getLastObject();

        // Verify first page
        Assertions.assertEquals(10, response.getFollowees().size());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertNotNull(response.getLastObject());


        // Get and verify second page
        request = new FollowingRequest(user, 10, last);
        response = followingDAOSpy.getFollowees(request);

        String second = response.getLastObject();

        Assertions.assertEquals(10, response.getFollowees().size());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(second, response.getLastObject());
        Assertions.assertNotEquals(last, response.getLastObject());


    }
}
