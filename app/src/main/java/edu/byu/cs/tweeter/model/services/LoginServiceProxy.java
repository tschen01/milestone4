package edu.byu.cs.tweeter.model.services;


import java.io.IOException;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.LoginService;
import byu.cs.cs340.model.services.request.LoginRequest;
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
    public LogoutResponse logout() throws IOException {
        return serverFacade.logout(URL_PATH_LOGOUT);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws IOException {
        LoginResponse response = serverFacade.loginResponse(request, URL_PATH_LOGIN);
        this.currentUser = response.getUser();
        return response;
    }

    @Override
    public SearchUserResponse searchUser(SearchUserRequest alias) throws IOException {
        return serverFacade.searchUser(alias, URL_PATH_SEARCH);
    }
}
