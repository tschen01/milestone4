package byu.cs.cs340.server.dao;

import java.util.List;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;

public class FollowUnfollowDAO {

    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) {

        if (request.getFollow().getFollowee().getFirstName().equals("invalid") || request.getFollow().getFollowee().getLastName().equals("invalid")
        || request.getFollow().getFollower().getFirstName().equals("invalid") || request.getFollow().getFollower().getLastName().equals("invalid")) {
            return new FollowUnfollowResponse(false);
        }

        return new FollowUnfollowResponse(true, true);
    }

    public IfFollowingResponse following(IfFollowingRequest user) {
        List<User> follower = getUserGenerator().generateUsers(20);
        follower.add(new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
        return new IfFollowingResponse(follower.contains(user.getUser()));
    }

    public UserGenerator getUserGenerator() {
        return UserGenerator.getInstance();
    }

}
