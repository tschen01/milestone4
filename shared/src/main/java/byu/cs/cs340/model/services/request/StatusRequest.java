package byu.cs.cs340.model.services.request;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;

public class StatusRequest extends Request {
    private User user;
    private int limit;
    private String lastStatus;

    public StatusRequest() {}

    public StatusRequest(User user, int limit, String lastStatus) {
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }
}
