package byu.cs.cs340.server.lambda.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;
import byu.cs.cs340.server.services.LoginServiceImp;
import byu.cs.cs340.server.services.SignUpServiceImp;

public class SignUpHandler implements RequestHandler<SignUpRequest, SignUpResponse> {

    @Override
    public SignUpResponse handleRequest(SignUpRequest request, Context context) {
        SignUpServiceImp service = new SignUpServiceImp();
        return service.signUp(request);
    }
}
