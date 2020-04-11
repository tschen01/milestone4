package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;

public class SignUpHandlerTest {

    private UserDAO userDAO = new UserDAO();
    User testUser = new User("Test", "User", "@TestUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    @Test
    public void testSignUp_sucess() {
        SignUpRequest request = new SignUpRequest("User10000", "10000", "User", "10000", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        SignUpResponse response = userDAO.signUpResponse(request);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void testInvalidUsername_fail() {
        SignUpRequest request = new SignUpRequest("TestUser", "User", "Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        SignUpResponse response = userDAO.signUpResponse(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());
    }
}
