package byu.cs.cs340.model.services.response;


import java.util.List;

import byu.cs.cs340.model.domain.User;

public class FolloweeResponse extends PagedResponse {
    private List<User> followers;

    public FolloweeResponse(String message) {
        super(false,message,false);
    }

    public FolloweeResponse(List<User> followers, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followers = followers;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
}
