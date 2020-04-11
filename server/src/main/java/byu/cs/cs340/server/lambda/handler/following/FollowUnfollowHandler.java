package byu.cs.cs340.server.lambda.handler.following;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.server.services.AuthkeyService;
import byu.cs.cs340.server.services.FollowingServiceImpl;

public class FollowUnfollowHandler implements RequestHandler<FollowUnfollowRequest, FollowUnfollowResponse> {

    @Override
    public FollowUnfollowResponse handleRequest(FollowUnfollowRequest request, Context context) {
        if (!verifyAuthkey(request.getAuthkey())) {
            throw new RuntimeException("[500Error]: wrong auth key");
        }
        FollowingServiceImpl service = new FollowingServiceImpl();
        FollowUnfollowResponse response = service.followUnfollow(request);
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

    private boolean verifyAuthkey(String authkey) {
        AuthkeyService service = new AuthkeyService();
        return service.verify(authkey);
    }

}

