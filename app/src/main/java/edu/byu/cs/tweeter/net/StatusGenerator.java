package edu.byu.cs.tweeter.net;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusGenerator {

    private static List<Status> allStatus = new ArrayList<>();

    private static StatusGenerator instance;

    private int count = 0;

    private Timestamp time;

    private StatusGenerator() { }

    public static StatusGenerator getInstance() {
        if (instance == null) {
            instance = new StatusGenerator();
        }
        return instance;
    }

    void addStatus(Status status) {
        allStatus.add(status);
    }

    void generateStatus(User user) {
        count++;
        time = new Timestamp(System.currentTimeMillis());
        allStatus.add(new Status(user, String.valueOf(count), time));
    }

    void clearAllStatus() {
        allStatus = new ArrayList<>();
    }

    List<Status> getPersonalStatus(User user) {
        List<Status> statuses = new ArrayList<>();

        for (Status status: allStatus) {
            if (status.getUser() == user) {
                statuses.add(status);
            }
        }

        return statuses;
    }

    public void clearInstance() {
        instance = null;
    }

}
