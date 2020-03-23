package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.cs.cs340.model.services.response.LogoutResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;

public class LogoutTask extends AsyncTask<Void, Void, LogoutResponse> {

    private final LoginPresenter presenter;
    private final LogoutObserver observer;

    public interface LogoutObserver {
        void logoutRetrieved(LogoutResponse logoutResponse);
    }


    public LogoutTask(LoginPresenter presenter, LogoutObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LogoutResponse doInBackground(Void... voids) {
        LogoutResponse response = null;
        try {
            response = presenter.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    protected void onPostExecute(LogoutResponse logoutResponse) {
        if(observer != null) {
            observer.logoutRetrieved(logoutResponse);
        }
    }

}
