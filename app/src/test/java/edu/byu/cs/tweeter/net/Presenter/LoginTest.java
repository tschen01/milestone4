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
import edu.byu.cs.tweeter.presenter.LoginPresenter;

public class LoginTest {
    private LoginService loginService = LoginService.getInstance();
    private LoginRequest request;
    private LoginResponse response;
    public class ViewImplementation implements LoginPresenter.View {
    }
    private LoginPresenter presenter = new LoginPresenter(new ViewImplementation()); //TODO: Check this
    @AfterEach
    void cleanUp(){
        request = null;
        response = null;
        loginService.clearInstance();
        LoginService.getInstance().logout();
    }
    @Test
    void testBasicLoginTestWithTestUser(){
        request = new LoginRequest("@TestUser", "password");
        response = presenter.login(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(presenter.getCurrentUser().getFirstName(), request.getUsername());
    }
    @Test
    void testInvalidUserCredentials(){
        request = new LoginRequest("invalid", "password");
        response = presenter.login(request);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(presenter.getCurrentUser());
    }
    @Test
    void testInvalidPassword(){
        request = new LoginRequest("@TestUser", "invalid");
        response = presenter.login(request);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(presenter.getCurrentUser());
    }
    @Test
    void testLoginUserThatJustSignedUp(){
        SignUpRequest signUpRequest = new SignUpRequest("Username5", "password", "Username5", "password", null);
        SignUpResponse signUpResponse = SignUpService.getInstance().signUp(signUpRequest);
        Assertions.assertTrue(signUpResponse.isSuccess());
        Assertions.assertNotNull(presenter.getCurrentUser());
        User signedInUser = presenter.getCurrentUser();
        request = new LoginRequest("Username5", "password");
        response = presenter.login(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(presenter.getCurrentUser());
        Assertions.assertEquals(presenter.getCurrentUser(), signedInUser);
    }
}
