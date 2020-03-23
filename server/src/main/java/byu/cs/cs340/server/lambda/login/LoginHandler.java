package byu.cs.cs340.server.lambda.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.server.services.LoginServiceImp;

public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {

    @Override
    public LoginResponse handleRequest(LoginRequest request, Context context) {
        LoginServiceImp service = new LoginServiceImp();
        LoginResponse response = service.login(request);


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
