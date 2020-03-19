package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.SignUpResponse;

public class SignUpService {
    private static SignUpService instance;

    private final ServerFacade serverFacade;

    public static SignUpService getInstance() {
        if(instance == null) {
            instance = new SignUpService();
        }

        return instance;
    }

    private SignUpService() {
        serverFacade = new ServerFacade();
    }

    public SignUpResponse signUp(SignUpRequest request) {
        SignUpResponse signUpResponse = serverFacade.signUpResponse(request);
        if (signUpResponse.isSuccess()) {
            LoginService.getInstance().login(new LoginRequest(request.getFirstName(), request.getLastName()));
        }
        return signUpResponse;
    }

}
