package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.LogoutRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import edu.byu.cs.tweeter.model.services.LoginServiceProxy;

public class LoginPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }
    public LoginPresenter(View view) {
        this.view = view;
    }

    public LoginResponse login(LoginRequest request) throws IOException {
        return LoginServiceProxy.getInstance().login(request);
    }

    public LogoutResponse logout() throws IOException {
        return LoginServiceProxy.getInstance().logout(new LogoutRequest());
    }

    public SearchUserResponse searchUser(SearchUserRequest alias) throws IOException {
        return LoginServiceProxy.getInstance().searchUser(alias);
    }
}
