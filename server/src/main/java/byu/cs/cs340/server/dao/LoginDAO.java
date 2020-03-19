package byu.cs.cs340.server.dao;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;

public class LoginDAO {
    public LoginResponse loginResponse(LoginRequest request) {
        if (request.getUsername().equals("invalid") || request.getPassword().equals("invalid")) {
            return new LoginResponse(false);
        }
        User user = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);

        return new LoginResponse(true, user);
    }

}
