package edu.byu.cs.tweeter.net.response;

public class FollowUnfollowResponse extends Response {

    private Boolean unfollowed;

    public FollowUnfollowResponse(boolean success) {
        super(success);
    }
    public FollowUnfollowResponse(boolean success, String error) {
        super(success, error);
    }

    public FollowUnfollowResponse(boolean success, Boolean unfollowed) {
        super(success);
        this.unfollowed = unfollowed;
    }

    public Boolean getUnfollowed() {
        return unfollowed;
    }
}
