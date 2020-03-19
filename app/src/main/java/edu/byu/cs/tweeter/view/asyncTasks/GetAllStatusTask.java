package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetAllStatusTask extends AsyncTask<StatusRequest, Void, StatusResponse> {
    private final StatusPresenter presenter;
    private final GetAllStatusObserver observer;

    public interface GetAllStatusObserver {
        void statusesRetrieved(StatusResponse followingResponse);
    }

    public GetAllStatusTask(StatusPresenter presenter, GetAllStatusObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected StatusResponse doInBackground(StatusRequest... followingRequests) {
        StatusResponse response = presenter.getAllStatuses(followingRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(StatusResponse response) {
        for (edu.byu.cs.tweeter.model.domain.Status status : response.getStatuses()) {

            Drawable drawable;

            try {
                drawable = ImageUtils.drawableFromUrl(status.getUser().getImageUrl());
            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }

            ImageCache.getInstance().cacheImage(status.getUser(), drawable);
        }
    }

    @Override
    protected void onPostExecute(StatusResponse statusResponse) {

        if(observer != null) {
            observer.statusesRetrieved(statusResponse);
        }
    }

}
