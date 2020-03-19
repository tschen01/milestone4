package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;


public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    private final LoginPresenter presenter;
    private final LoginObserver observer;

    public interface LoginObserver {
        void loginRetrieved(LoginResponse loginResponse);
    }


    public LoginTask(LoginPresenter presenter, LoginObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        LoginResponse response = presenter.login(loginRequests[0]);
        return response;
    }


    @Override
    protected void onPostExecute(LoginResponse loginResponse) {
        if(observer != null) {
            observer.loginRetrieved(loginResponse);
        }
    }

}
