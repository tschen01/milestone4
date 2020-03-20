package byu.cs.cs340.model.services;


import java.io.IOException;

import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;

public interface SignUpService {
    public SignUpResponse signUp(SignUpRequest request) throws IOException;

}
