package byu.cs.cs340.server.lambda.handler.following;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import byu.cs.cs340.server.services.FollowingServiceImpl;

public class FollowingHandler implements RequestHandler<IfFollowingRequest, IfFollowingResponse> {

    @Override
    public IfFollowingResponse handleRequest(IfFollowingRequest request, Context context) {
        FollowingServiceImpl service = new FollowingServiceImpl();
        IfFollowingResponse response = service.following(request);
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

