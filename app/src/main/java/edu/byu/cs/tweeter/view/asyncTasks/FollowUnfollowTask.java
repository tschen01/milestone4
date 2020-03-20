package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;

public class FollowUnfollowTask extends AsyncTask<FollowUnfollowRequest, Void, FollowUnfollowResponse> {

    private final FollowingPresenter presenter;
    private final FollowUnfollowObserver observer;

    public interface FollowUnfollowObserver {
        void followUnfollowRetrieved(FollowUnfollowResponse response);
    }


    public FollowUnfollowTask(FollowingPresenter presenter, FollowUnfollowObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowUnfollowResponse doInBackground(FollowUnfollowRequest... requests) {
        FollowUnfollowResponse response = presenter.followUnfollow(requests[0]);
        return response;
    }


    @Override
    protected void onPostExecute(FollowUnfollowResponse response) {
        if(observer != null) {
            observer.followUnfollowRetrieved(response);
        }
    }
}
