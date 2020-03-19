package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.LogoutResponse;
import edu.byu.cs.tweeter.net.response.SearchUserResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

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
        return LoginService.getInstance().login(request);
    }

    public LogoutResponse logout() {
        return LoginService.getInstance().logout();
    }

    public SearchUserResponse searchUser(String alias) {
        return LoginService.getInstance().searchUser(alias);
    }
}
