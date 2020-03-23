package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;

public class SearchUserTask extends AsyncTask<SearchUserRequest, Void, SearchUserResponse> {

    private final LoginPresenter presenter;
    private final SearchUserObserver observer;

    public interface SearchUserObserver {
        void searchUserRetrieved(SearchUserResponse response);
    }


    public SearchUserTask(LoginPresenter presenter, SearchUserObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected SearchUserResponse doInBackground(SearchUserRequest... requests) {
        SearchUserResponse response = null;
        try {
            response = presenter.searchUser(requests[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(SearchUserResponse response) {
        if(observer != null) {
            observer.searchUserRetrieved(response);
        }
    }
}
