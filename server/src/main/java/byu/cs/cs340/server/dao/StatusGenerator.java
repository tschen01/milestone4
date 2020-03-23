package byu.cs.cs340.server.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;

public class StatusGenerator {

    private static StatusGenerator statusGenerator;

    /**
     * A private constructor that ensures no instances of this class can be created from outside
     * the class.
     */
    private StatusGenerator() {}

    /**
     * Returns the singleton instance of the class
     *
     * @return the instance.
     */
    public static StatusGenerator getInstance() {
        if(statusGenerator == null) {
            statusGenerator = new StatusGenerator();
        }

        return statusGenerator;
    }

    public List<Status> generateUsersAndStatus(int userCount) {
        List<User> users = UserGenerator.getInstance().generateUsers(userCount);
        return generateStatusForUsers(users);
    }

    @SuppressWarnings("WeakerAccess")
    public List<Status> generateStatusForUsers(List<User> users) {
        List<Status> statuses = new ArrayList<>();

        // Add the test user and make him follow everyone
        User testUser = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);

        statuses.add(new Status(testUser, "@TestUser", "AAAAAAAAAAAAAAAAAAAA"));
        statuses.add(new Status(testUser, "www.google.com", "AAAAAAAAAAAAAAAAAAAA"));

        for(int i = 0; i < 20; i++) {
            statuses.add(new Status(testUser, String.valueOf(i), String.valueOf(i)));
        }
        for (int i = 0; i < users.size(); i++) {
            statuses.add(new Status(users.get(i), String.valueOf(i), String.valueOf(i)));
        }
        // Sort by the specified sort order
        Collections.sort(statuses, new Comparator<Status>() {
            @Override
            public int compare(Status status1, Status status2) {
                int result = status2.getTimestamp().compareTo(
                        status1.getTimestamp());

                if(result == 0) {
                    return status2.getTimestamp().compareTo(
                            status1.getTimestamp());
                }
                return result;
            }
        });

        return statuses;
    }

    public static void addStatus(Status newStatues) {

    }
}
