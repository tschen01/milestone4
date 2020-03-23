package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import byu.cs.cs340.model.domain.Follow;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;

public class FollowUnfollowDAOTest {
    private final User user1 = new User("valid", "valid", "");
    private final User user2 = new User("valid", "valid", "");
    private final User user3 = new User("invalid", "valid", "");
    private final User user4 = new User("valid", "invalid", "");

    private final Follow follow1 = new Follow(user1,  user2);
    private final Follow follow2 = new Follow(user2,  user3);
    private final Follow follow3 = new Follow(user3,  user4);

    private final List<User> users = Arrays.asList(user1,user2,user3);

    private FollowUnfollowDAO followUnfollowDAO;

    @BeforeEach
    void setup() {
        followUnfollowDAO = Mockito.spy(new FollowUnfollowDAO());

        UserGenerator mockUserGenerator = Mockito.mock(UserGenerator.class);
        Mockito.when(mockUserGenerator.generateUsers(Mockito.anyInt())).thenReturn(users);

        Mockito.when(followUnfollowDAO.getUserGenerator()).thenReturn(mockUserGenerator);
    }

    @Test
    void testUnfollow_succuess() {
        FollowUnfollowRequest request = new FollowUnfollowRequest(follow1);
        FollowUnfollowResponse response = followUnfollowDAO.followUnfollow(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertTrue(response.getUnfollowed());
    }

    @Test
    void testUnfollow_fail() {
        FollowUnfollowRequest request = new FollowUnfollowRequest(follow2);
        FollowUnfollowResponse response = followUnfollowDAO.followUnfollow(request);

        Assertions.assertFalse(response.isSuccess());

        FollowUnfollowRequest request1 = new FollowUnfollowRequest(follow3);
        FollowUnfollowResponse response1 = followUnfollowDAO.followUnfollow(request1);

        Assertions.assertFalse(response1.isSuccess());
    }

    @Test
    void testIfFollowing_success() {
        IfFollowingRequest request = new IfFollowingRequest(user1);
        IfFollowingResponse response = followUnfollowDAO.following(request);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void testIfFollowing_fail() {
        IfFollowingRequest request = new IfFollowingRequest(user4);
        IfFollowingResponse response = followUnfollowDAO.following(request);

        Assertions.assertFalse(response.isSuccess());
    }
}
