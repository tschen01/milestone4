package byu.cs.cs340.model.services;


import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;

public interface SignUpService {
    public SignUpResponse signUp(SignUpRequest request);

}
