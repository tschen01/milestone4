package byu.cs.cs340.server.lambda.following;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.server.services.FollowingServiceImpl;

public class FollowingHandler implements RequestHandler<User, Boolean> {

    @Override
    public Boolean handleRequest(User request, Context context) {
        FollowingServiceImpl service = new FollowingServiceImpl();
        return service.following(request);
    }
}

