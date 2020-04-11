package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.StatusResponse;
import byu.cs.cs340.server.lambda.handler.status.GetFeedHandler;

public class FeedHandlerTest {


    private GetFeedHandler feedHandler = new GetFeedHandler();


    @Test
    void testGetFeed_userStatus() {
        User user = new User("Test ", "User", "@TestUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        StatusRequest request = new StatusRequest(user, Integer.MAX_VALUE, null);
        request.setAuthkey("authkey");
        StatusResponse response = feedHandler.handleRequest(request, null);

        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(10, response.getStatuses().size());
        Assertions.assertNotNull(response.getLastObject());
    }
    @Test
    void testGetFeed_noUserStatus() {
        User user = new User("Test ", "User", "@TestUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        StatusRequest request = new StatusRequest(user, 10, null);
        request.setAuthkey("authkey");
        StatusResponse response = feedHandler.handleRequest(request, null);

        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(10, response.getStatuses().size());
        Assertions.assertNotNull(response.getLastObject());

        String lastTime = response.getLastObject();

        request = new StatusRequest(user, 10, lastTime);
        request.setAuthkey("authkey");
        response = feedHandler.handleRequest(request, null);

        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(10, response.getStatuses().size());
        Assertions.assertNotNull(response.getLastObject());
        Assertions.assertNotEquals(lastTime, response.getLastObject());
    }



}
