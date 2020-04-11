package byu.cs.cs340.server.lambda.handler.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.server.services.LoginServiceImp;

public class SearchUserHandler implements RequestHandler<SearchUserRequest, SearchUserResponse> {

    @Override
    public SearchUserResponse handleRequest(SearchUserRequest request, Context context) {

        LoginServiceImp service = new LoginServiceImp();
        SearchUserResponse response = service.searchUser(request);

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
