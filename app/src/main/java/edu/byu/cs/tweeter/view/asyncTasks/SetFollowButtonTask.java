package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;

public class SetFollowButtonTask extends AsyncTask<IfFollowingRequest, Void, IfFollowingResponse> {

    private final FollowingPresenter presenter;
    private final FollowButtonObserver observer;

    public interface FollowButtonObserver {
        void followUnfollowRetrieved(IfFollowingResponse response);
    }


    public SetFollowButtonTask(FollowingPresenter presenter, FollowButtonObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected IfFollowingResponse doInBackground(IfFollowingRequest... requests) {
        IfFollowingResponse response = null;
        try {
            response = presenter.following(requests[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    protected void onPostExecute(IfFollowingResponse response) {
        if(observer != null) {
            observer.followUnfollowRetrieved(response);
        }
    }
}
