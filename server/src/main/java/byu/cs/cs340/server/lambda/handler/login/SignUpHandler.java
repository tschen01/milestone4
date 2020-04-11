package byu.cs.cs340.server.lambda.handler.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;
import byu.cs.cs340.server.services.SignUpServiceImp;

public class SignUpHandler implements RequestHandler<SignUpRequest, SignUpResponse> {

    @Override
    public SignUpResponse handleRequest(SignUpRequest request, Context context) {
        SignUpServiceImp service = new SignUpServiceImp();
        SignUpResponse response = service.signUp(request);
        if (response == null) {
            throw new RuntimeException("[500Error]");
        }
        if (response.isSuccess()) {
            return response;
        }
        if (!response.isSuccess()) {
            throw new RuntimeException("[400Error]");
        }
        else {
            throw new RuntimeException("[500Error]");
        }
    }
}
