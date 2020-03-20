package edu.byu.cs.tweeter.view.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;

import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.services.LoginServiceProxy;
import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.CreateStatusTask;

public class CreateStatusActivity extends AppCompatActivity implements StatusPresenter.View, CreateStatusTask.CreateObserver {

    EditText editText;
    Button postButton;
    StatusPresenter presenter;
    CreateStatusTask.CreateObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_status);
        editText = findViewById(R.id.editText);
        postButton = findViewById(R.id.postButton);
        postButton.setEnabled(false);

        presenter = new StatusPresenter(this);
        observer = this;

        TextWatcher buttonWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editText.getText().toString().equals("")) {
                    postButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        editText.addTextChangedListener(buttonWatcher);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "post", Toast.LENGTH_SHORT).show();
                CreateStatusTask createStatusTask = new CreateStatusTask(presenter, observer);
                CreateStatusRequest request = new CreateStatusRequest(LoginServiceProxy.getInstance().getCurrentUser(), editText.getText().toString(),new Timestamp(System.currentTimeMillis()));
                createStatusTask.execute(request);
            }
        });

    }

    @Override
    public void CreateRetrieved(CreateStatusResponse response) {
        finish();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

    }
}
