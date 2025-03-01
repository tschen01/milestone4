package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;
import edu.byu.cs.tweeter.presenter.SignUpPresenter;

public class SignUpTask extends AsyncTask<SignUpRequest, Void, SignUpResponse> {

    private final SignUpPresenter presenter;
    private final SignUpObserver observer;

    public interface SignUpObserver {
        void signUpRetrieved(SignUpResponse signUpResponse);
    }


    public SignUpTask(SignUpPresenter presenter, SignUpObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected SignUpResponse doInBackground(SignUpRequest... signUpRequest) {
        SignUpResponse response = null;
        try {
            response = presenter.signUp(signUpRequest[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    protected void onPostExecute(SignUpResponse response) {
        if(observer != null) {
            observer.signUpRetrieved(response);
        }
    }

}
