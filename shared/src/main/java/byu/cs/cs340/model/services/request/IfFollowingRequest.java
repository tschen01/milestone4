package byu.cs.cs340.model.services.request;

import byu.cs.cs340.model.domain.User;

public class IfFollowingRequest {

    String follower_alias;
    String followee_alias;

    public IfFollowingRequest() {}

    public IfFollowingRequest(String follower_alias, String followee_alias) {
        this.follower_alias = follower_alias;
        this.followee_alias = followee_alias;
    }

    public void setFollowee_alias(String followee_alias) {
        this.followee_alias = followee_alias;
    }

    public void setFollower_alias(String follower_alias) {
        this.follower_alias = follower_alias;
    }

    public String getFollowee_alias() {
        return followee_alias;
    }

    public String getFollower_alias() {
        return follower_alias;
    }
}
