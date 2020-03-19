package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.LogoutResponse;
import edu.byu.cs.tweeter.net.response.SearchUserResponse;

public class LoginService {

    private static LoginService instance;

    private final ServerFacade serverFacade;

    private LoginResponse loginResponse;

    private String authToken;

    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    private LoginService() {
        serverFacade = new ServerFacade();
    }

    public void clearInstance() {
        instance = null;
    }

    public User getCurrentUser() {
        return loginResponse.getUser();
    }

    public LoginResponse login(LoginRequest request) {
        loginResponse = serverFacade.loginResponse(request);
        if (loginResponse.isSuccess()) {
            serverFacade.setData(loginResponse.getUser());
        }
        return loginResponse;
    }

    public LogoutResponse logout() {
        return serverFacade.logout();
    }

    public SearchUserResponse searchUser(String alias) {
        return serverFacade.searchUser(alias);
    }

}
