package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
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
        LoginResponse response = null;
        try {
            System.out.println("logining in");
            response = presenter.login(loginRequests[0]);
        } catch (Exception e) {
            System.out.println("error logining in");
            e.printStackTrace();
        }
        return response;
    }


    @Override
    protected void onPostExecute(LoginResponse loginResponse) {
        if(observer != null) {
            observer.loginRetrieved(loginResponse);
        }
    }

}
