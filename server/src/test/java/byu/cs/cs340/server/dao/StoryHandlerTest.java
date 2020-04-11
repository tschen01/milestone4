package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.StatusResponse;
import byu.cs.cs340.server.lambda.handler.status.GetStoryHandler;

public class StoryHandlerTest {
    GetStoryHandler storyHandler = new GetStoryHandler();

    @Test
    void testGetFeed_userStatus() {
        User user = new User("User", "1", "@User1", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        StatusRequest request = new StatusRequest(user, 10, null);
        request.setAuthkey("authkey");
        StatusResponse response = storyHandler.handleRequest(request, null);

        Assertions.assertTrue(!response.getHasMorePages());
        Assertions.assertEquals(1, response.getStatuses().size());
        Assertions.assertNull(response.getLastObject());
    }

}
