package byu.cs.cs340.model.services.request;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;

public class StatusRequest {
    private final User user;
    private final int limit;
    private final Status lastStatus;

    public StatusRequest(User user, int limit, Status lastStatus) {
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public User getUser() {
        return user;
    }

    public int getLimit() {
        return limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }
}
