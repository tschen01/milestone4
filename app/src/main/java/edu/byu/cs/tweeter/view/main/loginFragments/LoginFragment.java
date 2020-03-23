package edu.byu.cs.tweeter.view.main.loginFragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.main.LoginActivity;
import edu.byu.cs.tweeter.view.main.MainActivity;

public class LoginFragment extends Fragment implements LoginPresenter.View, LoginTask.LoginObserver {

    private Button loginButton;
    private Button registerButton;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private LoginPresenter loginPresenter;
    private LoginTask.LoginObserver logingObserver = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (Button) view.findViewById(R.id.login_button);
        registerButton = (Button) view.findViewById(R.id.register_button);

        loginPresenter = new LoginPresenter(this);

        //make button disabled in the beginning
        loginButton.setEnabled(false);
        registerButton.setEnabled(true);

        // a watch for the button
        TextWatcher buttonWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginButtonChange();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        // variable
        userNameEditText = (EditText) view.findViewById(R.id.userNameEditText);
        userNameEditText.addTextChangedListener(buttonWatcher);
        passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
        passwordEditText.addTextChangedListener(buttonWatcher);

        //do the login task
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginTask loginTask = new LoginTask(loginPresenter, logingObserver);
                LoginRequest request = new LoginRequest(userNameEditText.getText().toString(), passwordEditText.getText().toString());
                loginTask.execute(request);
            }
        });
        //loginRequest and loginTask

        //do the register task
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).turnToSignUpFragment();
            }
        });

        return view;
    }

    ;

    private void loginButtonChange() {
        if (!userNameEditText.getText().toString().equals("")
                && !passwordEditText.getText().toString().equals("")) {
            loginButton.setEnabled(true);
        } else {
            loginButton.setEnabled(false);
        }
    }

    @Override
    public void loginRetrieved(LoginResponse loginResponse) {
        if (loginResponse != null && loginResponse.isSuccess()) {
            getActivity().finish();
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
        }

    }
}