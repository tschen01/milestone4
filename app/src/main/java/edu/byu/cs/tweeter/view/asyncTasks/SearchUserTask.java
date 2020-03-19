package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.response.SearchUserResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;

public class SearchUserTask extends AsyncTask<String, Void, SearchUserResponse> {

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
    protected SearchUserResponse doInBackground(String... requests) {
        SearchUserResponse response = presenter.searchUser(requests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(SearchUserResponse response) {
        if(observer != null) {
            observer.searchUserRetrieved(response);
        }
    }
}
