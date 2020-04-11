package edu.byu.cs.tweeter.model.services;


import java.io.IOException;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.LoginService;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.LogoutRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import edu.byu.cs.tweeter.net.ServerFacade;

public class LoginServiceProxy implements LoginService {
    private static final String URL_PATH_LOGIN = "/login";
    private static final String URL_PATH_SEARCH = "/search";
    private static final String URL_PATH_LOGOUT = "/logout";

    private final ServerFacade serverFacade = new ServerFacade();


    private static LoginServiceProxy instance;

    private User currentUser;

    private String authToken;

    public static LoginServiceProxy getInstance() {
        if(instance == null) {
            instance = new LoginServiceProxy();
        }
        return instance;
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        logoutRequest.setAuthkey(authToken);
        return serverFacade.logout(logoutRequest, URL_PATH_LOGOUT);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws IOException {
        LoginResponse response = serverFacade.loginResponse(request, URL_PATH_LOGIN);
        this.currentUser = response.getUser();
        this.authToken = response.getAuthkey();
        return response;
    }

    @Override
    public SearchUserResponse searchUser(SearchUserRequest alias) throws IOException {
        return serverFacade.searchUser(alias, URL_PATH_SEARCH);
    }
}
