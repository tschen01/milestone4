package byu.cs.cs340.model.services.response;

public class FollowUnfollowResponse extends Response {

    private Boolean unfollowed;

    public FollowUnfollowResponse(boolean success) {
        super(success);
    }

    public FollowUnfollowResponse(boolean success, Boolean unfollowed) {
        super(success);
        this.unfollowed = unfollowed;
    }

    public Boolean getUnfollowed() {
        if (unfollowed == null) {
            return !isSuccess();
        }
        return unfollowed;
    }

    public void setUnfollowed(Boolean unfollowed) {
        this.unfollowed = unfollowed;
    }
}
