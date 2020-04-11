package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;

public class LoginHandlerTest {
    private UserDAO userDAO = new UserDAO();
    User testUser = new User("Test ", "User", "@TestUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    @Test
    public void testSignUp_sucess() {
        LoginRequest request = new LoginRequest("Test", "User");
        LoginResponse response = userDAO.loginResponse(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(testUser, response.getUser());
    }

    @Test
    public void testInvalidUsername_fail() {
        LoginRequest request = new LoginRequest("Test1", "User");
        LoginResponse response = userDAO.loginResponse(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());
    }
    @Test
    public void testInvalidPassword_fail() {
        LoginRequest request = new LoginRequest("Test", "User1");
        LoginResponse response = userDAO.loginResponse(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());

    }

    @Test
    void testSearchUser_success() {
        SearchUserRequest request = new SearchUserRequest("@TestUser");
        SearchUserResponse response = userDAO.searchUser(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(testUser, response.getUser());
    }

    @Test
    void testSearchUser_fail() {
        SearchUserRequest request = new SearchUserRequest("nope");
        SearchUserResponse response = userDAO.searchUser(request);

        Assertions.assertFalse(response.isSuccess());
    }
}
