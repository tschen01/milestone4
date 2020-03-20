package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import byu.cs.cs340.model.domain.User;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;

public class SetFollowButtonTask extends AsyncTask<User, Void, Boolean> {

    private final FollowingPresenter presenter;
    private final FollowButtonObserver observer;

    public interface FollowButtonObserver {
        void followUnfollowRetrieved(Boolean response);
    }


    public SetFollowButtonTask(FollowingPresenter presenter, FollowButtonObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected Boolean doInBackground(User... requests) {
        Boolean response = presenter.following(requests[0]);
        return response;
    }


    @Override
    protected void onPostExecute(Boolean response) {
        if(observer != null) {
            observer.followUnfollowRetrieved(response);
        }
    }
}
