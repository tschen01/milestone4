package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;

public class SignUpDAOTest {

    private SignUpDAO signUpDAO = new SignUpDAO();

    @Test
    public void testSignUp_sucess() {
        SignUpRequest request = new SignUpRequest("string1", "string2", "string3", "string4", "string5");
        SignUpResponse response = signUpDAO.signUpResponse(request);
        User testUser = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/mnames.json");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(testUser, response.getUser());
    }

    @Test
    public void testInvalidUsername_fail() {
        SignUpRequest request = new SignUpRequest("invalid", "string2", "string3", "string4", "string5");
        SignUpResponse response = signUpDAO.signUpResponse(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());
    }
    @Test
    public void testInvalidPassword_fail() {
        SignUpRequest request = new SignUpRequest("string1", "invalid", "string3", "string4", "string5");
        SignUpResponse response = signUpDAO.signUpResponse(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());

    }
}
