package byu.cs.cs340.server.services;


import javax.xml.ws.Response;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.FollowingService;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import byu.cs.cs340.server.dao.FollowUnfollowDAO;
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
        FollowUnfollowDAO dao = new FollowUnfollowDAO();
        return dao.followUnfollow(request);
    }

    @Override
    public IfFollowingResponse following(IfFollowingRequest request) {
        FollowUnfollowDAO dao = new FollowUnfollowDAO();
        return dao.following(request);
    }

    @Override
    public FolloweeResponse getFollowers(FolloweeRequest request) {
        FollowerDAO dao = new FollowerDAO();
        return dao.getFollowees(request);
    }
}
