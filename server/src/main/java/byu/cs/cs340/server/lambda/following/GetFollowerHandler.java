package byu.cs.cs340.server.lambda.following;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.server.services.FollowingServiceImpl;

public class GetFollowerHandler implements RequestHandler<FolloweeRequest, FolloweeResponse> {

    @Override
    public FolloweeResponse handleRequest(FolloweeRequest request, Context context) {
        FollowingServiceImpl service = new FollowingServiceImpl();
        FolloweeResponse response = service.getFollowers(request);

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
