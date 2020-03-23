package byu.cs.cs340.server.services;


import byu.cs.cs340.model.services.LoginService;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.server.dao.LoginDAO;

public class LoginServiceImp implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginDAO loginDAO = new LoginDAO();
        return loginDAO.loginResponse(request);
    }

    @Override
    public SearchUserResponse searchUser(SearchUserRequest alias) {
        LoginDAO loginDAO = new LoginDAO();
        return loginDAO.searchUser(alias);
    }

    @Override
    public LogoutResponse logout() {
        LoginDAO loginDAO = new LoginDAO();
        return loginDAO.logout();
    }
}
