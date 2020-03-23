package byu.cs.cs340.server.dao;

import java.util.List;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;

import static byu.cs.cs340.server.dao.UserGenerator.MALE_IMAGE_URL;

public class LoginDAO {
    public LoginResponse loginResponse(LoginRequest request) {
        if (request.getUsername().equals("invalid") || request.getPassword().equals("invalid")) {
            return new LoginResponse(false);
        }

        return new LoginResponse(true, new User("Test", "User", MALE_IMAGE_URL));
    }
    public SearchUserResponse searchUser(SearchUserRequest alias) {
        List<User> users = UserGenerator.getInstance().generateUsers(20);
        User testUser = new User("Test", "User", MALE_IMAGE_URL);
        users.add(testUser);

        for (User user : users) {
            if (user.getAlias().equals(alias.getAlias())) {
                return new SearchUserResponse(true, user);
            }
        }

        return new SearchUserResponse(false);

    }

    public LogoutResponse logout() {
        //  delete authkey
        return new LogoutResponse(true);
    }
}
