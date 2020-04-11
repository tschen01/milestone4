package byu.cs.cs340.server.services;


import byu.cs.cs340.model.services.FollowingService;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import byu.cs.cs340.server.dao.FollowerDAO;
import byu.cs.cs340.server.dao.FollowingDAO;

public class FollowingServiceImpl implements FollowingService {


    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {
        FollowingDAO followingDAO = new FollowingDAO();
        return followingDAO.getFollowees(request);
    }

    @Override
    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) {

        String follower_handle = request.getFollow().getFollower().getAlias();
        String followee_handle = request.getFollow().getFollowee().getAlias();
        FollowingDAO followingDAO = new FollowingDAO();
        FollowerDAO followerDAO = new FollowerDAO();

        try {
            if (followingDAO.isFollowing(follower_handle, followee_handle)) {
                followingDAO.deleteFollowing(follower_handle, followee_handle);
                followerDAO.deleteFollower(followee_handle, follower_handle);
                return new FollowUnfollowResponse(true, true);
            } else {
                String followee_name = request.getFollow().getFollowee().getName();
                String followee_image = request.getFollow().getFollowee().getImageUrl();
                String follower_name = request.getFollow().getFollower().getName();
                String follower_image = request.getFollow().getFollower().getImageUrl();
                followingDAO.addFollowing(follower_handle, followee_name, followee_image, followee_handle);
                followerDAO.addFollower(followee_handle, follower_name, follower_image, follower_handle);
                return new FollowUnfollowResponse(true, false);

            }
        } catch (Exception e) {
            return new FollowUnfollowResponse(false);
        }

    }

    @Override
    public IfFollowingResponse following(IfFollowingRequest request) {
        FollowingDAO dao = new FollowingDAO();
        return dao.following(request);
    }

    @Override
    public FolloweeResponse getFollowers(FolloweeRequest request) {
        FollowerDAO dao = new FollowerDAO();
        return dao.getFollowers(request);
    }
}
