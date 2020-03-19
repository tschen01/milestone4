package edu.byu.cs.tweeter.net.Presenter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.model.services.SignUpService;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
import edu.byu.cs.tweeter.presenter.SignUpPresenter;

public class SignUpTest {
    private SignUpService signUpService = SignUpService.getInstance();
    private SignUpRequest request;
    private SignUpResponse response;
    public class ViewImplementation implements SignUpPresenter.View {
    }
    private SignUpPresenter presenter = new SignUpPresenter(new ViewImplementation()); //TODO: Check this
    @AfterEach
    void cleanUp(){
        request = null;
        response = null;
        LoginService.getInstance().logout();
    }
    @Test
    void testBasicLoginTestWithTestUser(){
        request = new SignUpRequest("TestUser", "password", "TestUser", "password", "");
        response = presenter.signUp(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(presenter.getCurrentUser().getFirstName(), request.getUsername());
    }
    @Test
    void testInvalidUserCredentials(){
        request = new SignUpRequest("invalid", "password", "TestUser", "password", "");
        response = presenter.signUp(request);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());
    }
    @Test
    void testLoginUserThatJustSignedUp(){
        request = new SignUpRequest("Username5", "password", "Username5", "password", null);
        response = presenter.signUp(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(presenter.getCurrentUser());
        User signedInUser = presenter.getCurrentUser();
        LoginRequest loginRequest = new LoginRequest("Username5", "password");
        LoginResponse loginResponse = LoginService.getInstance().login(loginRequest);
        Assertions.assertTrue(loginResponse.isSuccess());
        Assertions.assertNotNull(presenter.getCurrentUser());
        Assertions.assertEquals(presenter.getCurrentUser(), signedInUser);
    }
}
