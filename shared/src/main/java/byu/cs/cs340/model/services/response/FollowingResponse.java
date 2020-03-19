package byu.cs.cs340.model.services.response;

import java.util.List;

import byu.cs.cs340.model.domain.User;

public class FollowingResponse extends PagedResponse {

    private List<User> followees;

    public FollowingResponse(boolean success) {
        super(false, false);
    }

    public FollowingResponse(List<User> followees, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followees = followees;
    }

    public List<User> getFollowees() {
        return followees;
    }
}
