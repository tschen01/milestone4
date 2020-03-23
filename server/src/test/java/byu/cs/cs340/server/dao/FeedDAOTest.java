package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import byu.cs.cs340.model.domain.Follow;
import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.StatusResponse;

public class FeedDAOTest {
    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", ""); // 2 followees
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", ""); // 6 followees
    private final User user6 = new User("Mother", "Teresa", ""); // 7 followees
    private final User user7 = new User("Harriett", "Hansen", "");
    private final User user8 = new User("Zoe", "Zabriski", "");
    private final User user9 = new User("Albert", "Awesome", ""); // 1  followee
    private final User user10 = new User("Star", "Student", "");
    private final User user11 = new User("Bo", "Bungle", "");
    private final User user12 = new User("Susie", "Sampson", "");

    private final Status status1 = new Status(user1, "1", "1");

    private final Status status2 = new Status(user2, "2", "2");
    private final Status status3 = new Status(user3, "3", "3");

    private final Status status4 = new Status(user4,  "4", "4");
    private final Status status5 = new Status(user5,  "5", "5");
    private final Status status6 = new Status(user6,  "6", "6");
    private final Status status7 = new Status(user7,  "7", "7");
    private final Status status8 = new Status(user8,  "8", "8");
    private final Status status9 = new Status(user9,  "9", "9");

    private final Status status10 = new Status(user10,  "10", "10");
    private final Status status11 = new Status(user11,  "11", "11");
    private final Status status12 = new Status(user12,  "12", "12");


    private final List<Status> status = Arrays.asList(status1, status2, status3, status4, status5, status6, status7,
            status8, status9, status10, status11, status12);

    private FeedDAO feedDAOSpy = new FeedDAO();

//    @BeforeEach
//    void setup() {
//        feedDAOSpy = Mockito.spy(new FeedDAO());
//
//        StatusGenerator mockStatusGenerator = Mockito.mock(StatusGenerator.class);
//        Mockito.when(mockStatusGenerator.generateUsersAndStatus(Mockito.anyInt())).thenReturn(status);
//
//        Mockito.when(feedDAOSpy.getStatusGenerator()).thenReturn(mockStatusGenerator);
//    }

    @Test
    void testGetFeed_noUserStatus() {
        StatusRequest request = new StatusRequest(user1, 10, null);
        StatusResponse response = feedDAOSpy.getAllStatusResponse(request);

        boolean contain = false;

        for (Status status: response.getStatuses()) {
            if (status.getUser().equals(user1)) {
                contain = true;
                break;
            }
        }
        Assertions.assertFalse(contain);
    }


    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {
        User testUser = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        StatusRequest request = new StatusRequest(testUser, 10, null);
        StatusResponse response = feedDAOSpy.getAllStatusResponse(request);

        Assertions.assertEquals(10, response.getStatuses().size());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertFalse(response.getStatuses().contains(status1));

        StatusRequest request1 = new StatusRequest(testUser, 10, response.getStatuses().get(9));
        StatusResponse response1 = feedDAOSpy.getAllStatusResponse(request1);

        Assertions.assertEquals(10, response1.getStatuses().size());
        Assertions.assertTrue(!response1.getHasMorePages());
        Assertions.assertFalse(response1.getStatuses().contains(status1));

    }


}
