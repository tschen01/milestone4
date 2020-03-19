package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
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
        SignUpResponse response = presenter.signUp(signUpRequest[0]);
        return response;
    }


    @Override
    protected void onPostExecute(SignUpResponse response) {
        if(observer != null) {
            observer.signUpRetrieved(response);
        }
    }

}
