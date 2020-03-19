package edu.byu.cs.tweeter.net.Presenter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.FollowingService;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.net.request.FolloweeRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.response.FollowUnfollowResponse;
import edu.byu.cs.tweeter.net.response.FolloweeResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;

public class FollowTest {

    private final User user = new User("Daffy", "Duck", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    private FollowingService followingService = FollowingService.getInstance();
    private FolloweeRequest followeeRequest;
    private FollowingRequest followingRequest;
    private FollowUnfollowRequest followUnfollowRequest;
    private FolloweeResponse followeeResponse;
    private FollowingResponse followingResponse;
    private FollowUnfollowResponse followUnfollowResponse;
    public class ViewImplementation implements FollowingPresenter.View {
    }
    private FollowingPresenter presenter = new FollowingPresenter(new ViewImplementation()); //TODO: Check this
    @BeforeEach
    void setUp() {
        LoginService.getInstance().login(new LoginRequest("TestUser", "password"));
    }
    @AfterEach
    void cleanUp(){
        followeeRequest = null;
        followingRequest = null;
        followUnfollowRequest = null;
        followeeResponse = null;
        followingResponse = null;
        followUnfollowResponse = null;
        LoginService.getInstance().logout();
    }
    @Test
    void testGetFollowingWithExistingUserPass(){
        followingRequest = new FollowingRequest(presenter.getCurrentUser(), 10, null);
        followingResponse = followingService.getFollowees(followingRequest);
        Assertions.assertTrue(followingResponse.isSuccess());
        Assertions.assertEquals(10, followingResponse.getFollowees().size());
    }
    @Test
    void testGetFolloweeWithExistingUserPass(){
        followeeRequest = new FolloweeRequest(presenter.getCurrentUser(), 10, null);
        followeeResponse = followingService.getFollowers(followeeRequest);
        Assertions.assertTrue(followeeResponse.isSuccess());
        Assertions.assertEquals(10, followeeResponse.getFollowers().size());
    }
    @Test
    void testGetFollowingWithExistingUserFail(){
        followingRequest = new FollowingRequest(null, 10, null);
        followingResponse = followingService.getFollowees(followingRequest);
        Assertions.assertFalse(followingResponse.isSuccess());
        Assertions.assertNull(followingResponse.getFollowees());
    }
    @Test
    void testGetFolloweeWithExistingUserFail(){
        followeeRequest = new FolloweeRequest(null, 10, null);
        followeeResponse = followingService.getFollowers(followeeRequest);
        Assertions.assertFalse(followeeResponse.isSuccess());
        Assertions.assertNull(followeeResponse.getFollowers());
    }
    @Test
    void testFollowUserPass(){
        User newUser = new User("user", "password", "url");
        followUnfollowRequest = new FollowUnfollowRequest(new Follow(presenter.getCurrentUser(), newUser));
        followUnfollowResponse = followingService.followUnfollow(followUnfollowRequest);
        Assertions.assertTrue(followUnfollowResponse.isSuccess());
        Assertions.assertFalse(followUnfollowResponse.getUnfollowed());
    }
    @Test
    void testUnfollowUserPass(){
        User newUser = new User("Test", "User", "");
        followUnfollowRequest = new FollowUnfollowRequest(new Follow(presenter.getCurrentUser(), newUser));
        followUnfollowResponse = followingService.followUnfollow(followUnfollowRequest);
        Assertions.assertTrue(followUnfollowResponse.isSuccess());
        Assertions.assertTrue(followUnfollowResponse.getUnfollowed());
    }

}
