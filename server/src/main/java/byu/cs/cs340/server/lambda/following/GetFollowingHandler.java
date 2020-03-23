package byu.cs.cs340.server.lambda.following;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.server.services.FollowingServiceImpl;

public class GetFollowingHandler implements RequestHandler<FollowingRequest, FollowingResponse> {

    @Override
    public FollowingResponse handleRequest(FollowingRequest request, Context context) {
        FollowingServiceImpl service = new FollowingServiceImpl();
        FollowingResponse response = service.getFollowees(request);

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
