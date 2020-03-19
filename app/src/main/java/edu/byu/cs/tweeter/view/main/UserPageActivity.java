package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.net.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.net.response.FollowUnfollowResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.FollowUnfollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.asyncTasks.SetFollowButtonTask;
import edu.byu.cs.tweeter.view.cache.DataCache;
import edu.byu.cs.tweeter.view.cache.ImageCache;

public class UserPageActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver, MainPresenter.View,
        SetFollowButtonTask.FollowButtonObserver, FollowingPresenter.View, FollowUnfollowTask.FollowUnfollowObserver {

    private MainPresenter presenter;
    private FollowingPresenter followingPresenter;
    private User user;
    private ImageView userImageView;
    private DataCache dataCache = DataCache.getInstance();
    private Button followButton;
    private FollowUnfollowTask.FollowUnfollowObserver observer = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        presenter = new MainPresenter(this);
        followingPresenter = new FollowingPresenter(this);

        userImageView = findViewById(R.id.userImage);
        followButton = findViewById(R.id.followButton);

        if (presenter.getCurrentUser().equals(DataCache.getInstance().getSelectedUser())) {
            followButton.setVisibility(View.GONE);
        }

        if (dataCache.getSelectedUser() != null) {
            user = dataCache.getSelectedUser();
        }
        else {
            user = presenter.getCurrentUser();
        }

        SetFollowButtonTask setFollowButtonTask = new SetFollowButtonTask(followingPresenter, this);
        setFollowButtonTask.execute(user);

        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(user.getImageUrl());

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        followButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FollowUnfollowTask followUnfollowTask = new FollowUnfollowTask(followingPresenter, observer);
                FollowUnfollowRequest request = new FollowUnfollowRequest(new Follow(presenter.getCurrentUser(), dataCache.getSelectedUser()));
                followUnfollowTask.execute(request);
            }
        });

    }

    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // We're just loading one image. No need to indicate progress.
    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(user, drawables[0]);

        if(drawables[0] != null) {
            userImageView.setImageDrawable(drawables[0]);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            DataCache.getInstance().setSelectedUser(null);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void followUnfollowRetrieved(Boolean response) {
        if (!response) {
            followButton.setText("follow");
            followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            followButton.setText("unfollow");
            followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void followUnfollowRetrieved(FollowUnfollowResponse response) {
        if (!response.getUnfollowed()) {
            followButton.setText("unfollow");
            followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        else {
            followButton.setText("follow");
            followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }
}