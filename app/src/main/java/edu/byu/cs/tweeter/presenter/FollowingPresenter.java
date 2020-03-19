package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.FollowingService;
import edu.byu.cs.tweeter.net.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.net.request.FolloweeRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.response.FollowUnfollowResponse;
import edu.byu.cs.tweeter.net.response.FolloweeResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;

public class FollowingPresenter extends Presenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public FollowingPresenter(View view) {
        this.view = view;
    }

    public FollowingResponse getFollowing(FollowingRequest request) {
        FollowingResponse response = FollowingService.getInstance().getFollowees(request);
        return response;
    }

    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) {
        return FollowingService.getInstance().followUnfollow(request);
    }

    public Boolean following(User user) {
        return FollowingService.getInstance().following(user);
    }

    public FolloweeResponse getFollowing(FolloweeRequest request) {
        FolloweeResponse response = FollowingService.getInstance().getFollowers(request);
        return response;
    }

}
