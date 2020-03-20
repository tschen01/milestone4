package byu.cs.cs340.server.dao;

import java.util.List;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.server.services.LoginServiceImp;

public class LoginDAO {
    public LoginResponse loginResponse(LoginRequest request) {
        if (request.getUsername().equals("invalid") || request.getPassword().equals("invalid")) {
            return new LoginResponse(false);
        }
        User user = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);

        return new LoginResponse(true, user);
    }
    public SearchUserResponse searchUser(String alias) {
        List<User> users = UserGenerator.getInstance().generateUsers(20);
        User testUser = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);
        users.add(testUser);

        for (User user : users) {
            if (user.getAlias().equals(alias)) {
                return new SearchUserResponse(true, user);
            }
        }

        return new SearchUserResponse(false);

    }

}
