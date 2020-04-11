package byu.cs.cs340.model.services.request;

import byu.cs.cs340.model.domain.User;

public class FollowingRequest extends Request{

    private User follower;
    private int limit;
    private String lastFollowee;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FollowingRequest() {}

    /**
     * Creates an instance.
     *
     * @param follower the {@link User} whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFollowee the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public FollowingRequest(User follower, int limit, String lastFollowee) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public User getFollower() {
        return follower;
    }

    /**
     * Sets the follower.
     *
     * @param follower the follower.
     */
    public void setFollower(User follower) {
        this.follower = follower;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets the limit.
     *
     * @param limit the limit.
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastFollowee() {
        return lastFollowee;
    }

    public void setLastFollowee(String lastFollowee) {
        this.lastFollowee = lastFollowee;
    }
}
