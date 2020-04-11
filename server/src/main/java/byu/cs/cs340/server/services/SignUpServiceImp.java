package byu.cs.cs340.server.services;


import byu.cs.cs340.model.services.SignUpService;
import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;
import byu.cs.cs340.server.dao.SignUpDAO;

public class SignUpServiceImp implements SignUpService {

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        SignUpDAO dao = new SignUpDAO();
        return dao.signUpResponse(request);
    }
}
