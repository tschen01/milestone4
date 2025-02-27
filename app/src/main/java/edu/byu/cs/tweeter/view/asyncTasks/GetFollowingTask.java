package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.response.FollowingResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetFollowingTask extends AsyncTask<FollowingRequest, Void, FollowingResponse> {

    private final FollowingPresenter presenter;
    private final GetFolloweesObserver observer;

    public interface GetFolloweesObserver {
        void followeesRetrieved(FollowingResponse followingResponse);
    }

    public GetFollowingTask(FollowingPresenter presenter, GetFolloweesObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowingResponse doInBackground(FollowingRequest... followingRequests) {
        FollowingResponse response = null;
        try {
            response = presenter.getFollowing(followingRequests[0]);
            loadImages(response);
        } catch (Exception e) {
            System.out.println("ERROR!!!!! " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    private void loadImages(FollowingResponse response) {
        for(User user : response.getFollowees()) {

            Drawable drawable;

            try {
                drawable = ImageUtils.drawableFromUrl(user.getImageUrl());
            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }

            ImageCache.getInstance().cacheImage(user, drawable);
        }
    }

    @Override
    protected void onPostExecute(FollowingResponse followingResponse) {

        if(observer != null) {
            observer.followeesRetrieved(followingResponse);
        }
    }
}
