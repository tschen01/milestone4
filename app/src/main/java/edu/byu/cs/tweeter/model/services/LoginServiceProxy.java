package edu.byu.cs.tweeter.model.services;


import java.io.IOException;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.LoginService;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import edu.byu.cs.tweeter.net.ServerFacade;

public class LoginServiceProxy implements LoginService {
    private static final String URL_PATH_LOGIN = "/login";
    private static final String URL_PATH_SEARCH = "/login";

    private final ServerFacade serverFacade = new ServerFacade();


    private static LoginServiceProxy instance;

    private LoginResponse loginResponse;

    private String authToken;

    public static LoginServiceProxy getInstance() {
        if(instance == null) {
            instance = new LoginServiceProxy();
        }
        return instance;
    }

    public LogoutResponse logout() {
        return new LogoutResponse(true);
    }

    public User getCurrentUser() {
        return loginResponse.getUser();
    }

    @Override
    public LoginResponse login(LoginRequest request) throws IOException {
        LoginResponse response = serverFacade.loginResponse(request, URL_PATH_LOGIN);
        loginResponse = response;
        return response;
    }

    @Override
    public SearchUserResponse searchUser(String alias) throws IOException {
        return serverFacade.searchUser(alias, URL_PATH_SEARCH);
    }
}
