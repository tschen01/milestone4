package byu.cs.cs340.server.services;


import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.LoginService;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.server.dao.LoginDAO;

public class LoginServiceImp implements LoginService {

    private static LoginServiceImp instance;

    private LoginResponse loginResponse;

    private String authToken;

    public static LoginServiceImp getInstance() {
        if(instance == null) {
            instance = new LoginServiceImp();
        }
        return instance;
    }

    @Override
    public User getCurrentUser() {
        return loginResponse.getUser();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginDAO loginDAO = new LoginDAO();
        return loginDAO.loginResponse(request);
    }

    @Override
    public SearchUserResponse searchUser(String alias) {
        return null;
    }
}
