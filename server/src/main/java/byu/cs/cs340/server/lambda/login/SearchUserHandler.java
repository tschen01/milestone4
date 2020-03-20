package byu.cs.cs340.server.lambda.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.server.services.LoginServiceImp;

public class SearchUserHandler implements RequestHandler<String, SearchUserResponse> {

    @Override
    public SearchUserResponse handleRequest(String request, Context context) {
        LoginServiceImp service = new LoginServiceImp();
        return service.searchUser(request);
    }
}
