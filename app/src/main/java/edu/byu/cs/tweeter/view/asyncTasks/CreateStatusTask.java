package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import edu.byu.cs.tweeter.presenter.StatusPresenter;

public class CreateStatusTask extends AsyncTask<CreateStatusRequest, Void, CreateStatusResponse> {

    private final StatusPresenter presenter;
    private final CreateObserver observer;

    public interface CreateObserver {
        void CreateRetrieved(CreateStatusResponse response);
    }


    public CreateStatusTask(StatusPresenter presenter, CreateObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected CreateStatusResponse doInBackground(CreateStatusRequest... requests) {
        CreateStatusResponse response = presenter.getCreateStatusResponse(requests[0]);
        return response;
    }


    @Override
    protected void onPostExecute(CreateStatusResponse response) {
        if(observer != null) {
            observer.CreateRetrieved(response);
        }
    }


}
