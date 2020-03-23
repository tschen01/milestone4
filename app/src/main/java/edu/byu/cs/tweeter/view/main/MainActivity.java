package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.response.LogoutResponse;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.cache.DataCache;
import edu.byu.cs.tweeter.view.cache.ImageCache;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class MainActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver, MainPresenter.View, LogoutTask.LogoutObserver, LoginPresenter.View {

    private MainPresenter presenter;
    private LoginPresenter loginPresenter;
    private User user;
    private ImageView userImageView;
    private Button logoutButton;
    private LogoutTask.LogoutObserver observer = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutButton = findViewById(R.id.logout_button);

        presenter = new MainPresenter(this);
        loginPresenter = new LoginPresenter(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getBaseContext(), CreateStatusActivity.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "post", Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutTask logoutTask = new LogoutTask(loginPresenter, observer);
                logoutTask.execute();
            }
        });

        userImageView = findViewById(R.id.userImage);

        user = presenter.getCurrentUser();

        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(user.getImageUrl());

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());
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
    public void logoutRetrieved(LogoutResponse logoutResponse) {
        if (logoutResponse != null && logoutResponse.isSuccess()) {
            DataCache.getInstance().setSelectedUser(null);
            finish();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            Toast.makeText(getBaseContext(),"logout failed", Toast.LENGTH_LONG).show();
        }
    }

}