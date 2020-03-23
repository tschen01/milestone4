package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;
import edu.byu.cs.tweeter.model.services.SignUpServiceProxy;

public class SignUpPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }
    public SignUpPresenter(View view) {
        this.view = view;
    }

    public SignUpResponse signUp(SignUpRequest request) throws IOException {
        SignUpServiceProxy serviceProxy = new SignUpServiceProxy();
        return serviceProxy.signUp(request);
    }

}
