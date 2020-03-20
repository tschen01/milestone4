package edu.byu.cs.tweeter.presenter;

import byu.cs.cs340.model.services.request.LoginRequest;
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

    public LoginResponse login(LoginRequest request) {
        return LoginServiceProxy.getInstance().login(request);
    }

    public LogoutResponse logout() {
        return LoginServiceProxy.getInstance().logout();
    }

    public SearchUserResponse searchUser(String alias) {
        return LoginServiceProxy.getInstance().searchUser(alias);
    }
}
