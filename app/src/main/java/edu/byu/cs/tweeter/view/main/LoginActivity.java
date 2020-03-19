package edu.byu.cs.tweeter.view.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.view.main.loginFragments.LoginFragment;
import edu.byu.cs.tweeter.view.main.loginFragments.SignUpFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment login = new LoginFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.Fragment_container,login,"Test Fragment");
        transaction.commit();
    }

    public void turnToSignUpFragment() {
        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Fragment_container,signUpFragment)
                .commit();

    }

}
