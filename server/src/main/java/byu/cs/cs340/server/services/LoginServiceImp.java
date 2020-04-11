package byu.cs.cs340.server.services;


import byu.cs.cs340.model.services.LoginService;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.LogoutRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.server.dao.UserDAO;

public class LoginServiceImp implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {
        UserDAO userDAO = new UserDAO();
        return userDAO.loginResponse(request);
    }

    @Override
    public SearchUserResponse searchUser(SearchUserRequest alias) {
        UserDAO userDAO = new UserDAO();
        return userDAO.searchUser(alias);
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        UserDAO userDAO = new UserDAO();
        return userDAO.logout(request);
    }
}
