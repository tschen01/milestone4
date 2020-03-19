package byu.cs.cs340.model.services;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;

public interface LoginService {

    public User getCurrentUser();

    public LoginResponse login(LoginRequest request);

    public SearchUserResponse searchUser(String alias);

}
