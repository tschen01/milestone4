package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;

public class LoginDAOTest {
    private LoginDAO loginDAO = new LoginDAO();

    @Test
    public void testSignUp_sucess() {
        LoginRequest request = new LoginRequest("string1", "string2");
        LoginResponse response = loginDAO.loginResponse(request);
        User testUser = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/mnames.json");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(testUser, response.getUser());
    }

    @Test
    public void testInvalidUsername_fail() {
        LoginRequest request = new LoginRequest("invalid", "string2");
        LoginResponse response = loginDAO.loginResponse(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());
    }
    @Test
    public void testInvalidPassword_fail() {
        LoginRequest request = new LoginRequest("string1", "invalid");
        LoginResponse response = loginDAO.loginResponse(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());

    }

    @Test
    void testSearchUser_success() {
        SearchUserRequest request = new SearchUserRequest("@TestUser");
        SearchUserResponse response = loginDAO.searchUser(request);
        User testUser = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/mnames.json");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(testUser, response.getUser());
    }

    @Test
    void testSearchUser_fail() {
        SearchUserRequest request = new SearchUserRequest("nope");
        SearchUserResponse response = loginDAO.searchUser(request);

        Assertions.assertFalse(response.isSuccess());
    }
}
