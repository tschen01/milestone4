package byu.cs.cs340.server.dao;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;

public class SignUpDAO {
    public SignUpResponse signUpResponse(SignUpRequest request) {
        if (request.getUsername().equals("invalid") || request.getPassword().equals("invalid")) {
            return new SignUpResponse(false);
        }

        User user = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);

        return new SignUpResponse(true, user);
    }

}
