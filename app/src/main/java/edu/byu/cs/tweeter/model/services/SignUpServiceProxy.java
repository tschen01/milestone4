package edu.byu.cs.tweeter.model.services;


import java.io.IOException;

import byu.cs.cs340.model.services.SignUpService;
import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;
import edu.byu.cs.tweeter.net.ServerFacade;

public class SignUpServiceProxy implements SignUpService {
    private static final String URL_PATH = "/signup";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public SignUpResponse signUp(SignUpRequest request) throws IOException {
        SignUpResponse response = serverFacade.signUpResponse(request, URL_PATH);
        LoginServiceProxy.getInstance().setCurrentUser(response.getUser());
        return response;
    }
}
