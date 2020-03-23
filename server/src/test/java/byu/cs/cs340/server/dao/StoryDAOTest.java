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

public class StoryDAOTest {
    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user10 = new User("Star", "Student", "");
    private final User user12 = new User("Susie", "Sampson", "");

    private final Status status1 = new Status(user1, "1", "1");

    private final Status status2 = new Status(user1, "2", "2");
    private final Status status3 = new Status(user1, "3", "3");

    private final Status status4 = new Status(user1,  "4", "4");
    private final Status status5 = new Status(user1,  "5", "5");
    private final Status status6 = new Status(user1,  "6", "6");
    private final Status status7 = new Status(user1,  "7", "7");
    private final Status status8 = new Status(user10,  "8", "8");
    private final Status status9 = new Status(user10,  "9", "9");

    private final Status status10 = new Status(user10,  "10", "10");
    private final Status status11 = new Status(user10,  "11", "11");
    private final Status status12 = new Status(user12,  "12", "12");


    private final List<Status> status = Arrays.asList(status1, status2, status3, status4, status5, status6, status7,
            status8, status9, status10, status11, status12);

    private StoryDAO storyDAOSpy;

    @BeforeEach
    void setup() {
        storyDAOSpy = Mockito.spy(new StoryDAO());

        StatusGenerator mockStatusGenerator = Mockito.mock(StatusGenerator.class);
        Mockito.when(mockStatusGenerator.generateUsersAndStatus(Mockito.anyInt())).thenReturn(status);

        Mockito.when(storyDAOSpy.getStatusGenerator()).thenReturn(mockStatusGenerator);
    }

    @Test
    void testGetStory_noUserStatus() {
        StatusRequest request = new StatusRequest(user2, 10, null);
        StatusResponse response = storyDAOSpy.getPersonalStatusResponse(request);

        Assertions.assertEquals(0, response.getStatuses().size());
    }


    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {

        StatusRequest request1 = new StatusRequest(user1, 5, null);
        StatusResponse response1 = storyDAOSpy.getPersonalStatusResponse(request1);

        Assertions.assertEquals(5, response1.getStatuses().size());
        Assertions.assertTrue(response1.getHasMorePages());
        Assertions.assertTrue(response1.getStatuses().contains(status1));

        StatusRequest request10 = new StatusRequest(user10, 10, null);
        StatusResponse response10 = storyDAOSpy.getPersonalStatusResponse(request10);

        Assertions.assertEquals(4, response10.getStatuses().size());
        Assertions.assertFalse(response10.getHasMorePages());
        Assertions.assertFalse(response10.getStatuses().contains(status1));

        StatusRequest request12 = new StatusRequest(user12, 10, null);
        StatusResponse response12 = storyDAOSpy.getPersonalStatusResponse(request12);

        Assertions.assertEquals(1, response12.getStatuses().size());
        Assertions.assertFalse(response12.getHasMorePages());
        Assertions.assertTrue(response12.getStatuses().contains(status12));
    }


}
