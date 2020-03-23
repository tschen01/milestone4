package byu.cs.cs340.server.lambda.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.server.services.LoginServiceImp;

public class LogoutHandler implements RequestHandler<Void, LogoutResponse> {

    @Override
    public LogoutResponse handleRequest(Void aVoid, Context context) {
        LoginServiceImp service = new LoginServiceImp();
        LogoutResponse response = service.logout();

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