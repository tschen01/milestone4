package edu.byu.cs.tweeter.view.main.loginFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.SignUpPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.SignUpTask;
import edu.byu.cs.tweeter.view.cache.DataCache;
import edu.byu.cs.tweeter.view.main.MainActivity;

import static android.app.Activity.RESULT_OK;

public class SignUpFragment extends Fragment implements SignUpPresenter.View, SignUpTask.SignUpObserver {

    private Button imageButton;
    private Button registerButton;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private SignUpPresenter presenter;
    private SignUpTask.SignUpObserver observer = this;
    private ImageView ivPreview;
    private Uri selectedImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        imageButton = (Button) view.findViewById(R.id.image_button);
        registerButton = (Button) view.findViewById(R.id.register_button);

        imageButton.setVisibility(View.GONE);

        presenter = new SignUpPresenter(this);

        //make button disabled in the beginning
        registerButton.setEnabled(false);

        // a watch for the button
        TextWatcher buttonWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerButtonChange();
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
        firstNameEditText = (EditText) view.findViewById(R.id.firstNameEditText);
        firstNameEditText.addTextChangedListener(buttonWatcher);
        lastNameEditText = (EditText) view.findViewById(R.id.lastNameEditText);
        lastNameEditText.addTextChangedListener(buttonWatcher);
        ivPreview = (ImageView) view.findViewById(R.id.ivPreview);

        ivPreview.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_alt_black_24dp));

        //access camera
        ivPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        //do the login task
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"upload image", Toast.LENGTH_LONG).show();
            }
        });
        //loginRequest and loginTask

        //do the register task
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SignUpTask loginTask = new SignUpTask(presenter, observer);
                SignUpRequest request = new SignUpRequest(getUsername(), getPassword(),getFirstName(), getLastName(), selectedImage.toString());
                loginTask.execute(request);
            }
        });

        return view;
    };

    private void registerButtonChange(){
        if(!userNameEditText.getText().toString().equals("")
                && !passwordEditText.getText().toString().equals("")
                && !firstNameEditText.getText().toString().equals("")
                && !lastNameEditText.getText().toString().equals("")) {
            registerButton.setEnabled(true);
        }
        else {
            registerButton.setEnabled(false);
        }
    };

    @Override
    public void signUpRetrieved(SignUpResponse signUpResponse) {
        if (signUpResponse != null && signUpResponse.isSuccess()) {
            getActivity().finish();
            DataCache.getInstance().setSelectedUser(signUpResponse.getUser());
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
        }

    }

    public String getUsername(){
        return userNameEditText.getText().toString();
    }
    public String getPassword(){
        return passwordEditText.getText().toString();
    }
    public String getFirstName(){
        return firstNameEditText.getText().toString();
    }
    public String getLastName(){
        return lastNameEditText.getText().toString();
    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                // Do something with imagePath

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ivPreview.setImageBitmap(photo);
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                selectedImage = getImageUri(getActivity(), photo);
                String realPath = getRealPathFromURI(selectedImage);
                selectedImage = Uri.parse(realPath);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}

