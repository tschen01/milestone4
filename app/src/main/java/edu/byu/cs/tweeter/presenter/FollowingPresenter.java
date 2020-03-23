package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import edu.byu.cs.tweeter.model.services.FollowingServiceProxy;

public class FollowingPresenter extends Presenter {

    private final View view;

    private FollowingServiceProxy serviceProxy = new FollowingServiceProxy();

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public FollowingPresenter(View view) {
        this.view = view;
    }

    public FollowingResponse getFollowing(FollowingRequest request) throws IOException {
        FollowingServiceProxy serviceProxy = new FollowingServiceProxy();
        return serviceProxy.getFollowees(request);
    }

    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) throws IOException {
        FollowingServiceProxy serviceProxy = new FollowingServiceProxy();
        return serviceProxy.followUnfollow(request);
    }

    public IfFollowingResponse following(IfFollowingRequest user) throws IOException {
        FollowingServiceProxy serviceProxy = new FollowingServiceProxy();
        return serviceProxy.following(user);
    }

    public FolloweeResponse getFollowing(FolloweeRequest request) throws IOException {
        FollowingServiceProxy serviceProxy = new FollowingServiceProxy();
        return serviceProxy.getFollowers(request);
    }

}
