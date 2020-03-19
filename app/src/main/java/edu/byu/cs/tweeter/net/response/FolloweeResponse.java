package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FolloweeResponse extends PagedResponse {
    private List<User> followers;

    public FolloweeResponse(boolean success) {
        super(false,false);
    }

    public FolloweeResponse(List<User> followers, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followers = followers;
    }

    public List<User> getFollowers() {
        return followers;
    }

}
