package byu.cs.cs340.model.services.response;

public class IfFollowingResponse extends Response {

    boolean following;

    public IfFollowingResponse(boolean success) {
        super(success);
    }
    public IfFollowingResponse(boolean success, boolean following) {
        super(success);
        this.following = following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
    public boolean getFollowing() {
        return following;
    }
}
