package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FolloweeRequest;
import edu.byu.cs.tweeter.net.response.FolloweeResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetFolloweeTask extends AsyncTask<FolloweeRequest, Void, FolloweeResponse> {

    private final FollowingPresenter presenter;
    private final GetFollowersObserver observer;

    public interface GetFollowersObserver {
        void followersRetrieved(FolloweeResponse followingResponse);
    }

    public GetFolloweeTask(FollowingPresenter presenter, GetFollowersObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FolloweeResponse doInBackground(FolloweeRequest... followingRequests) {
        FolloweeResponse response = presenter.getFollowing(followingRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(FolloweeResponse response) {
        for(User user : response.getFollowers()) {

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
    protected void onPostExecute(FolloweeResponse followingResponse) {

        if(observer != null) {
            observer.followersRetrieved(followingResponse);
        }
    }
}
