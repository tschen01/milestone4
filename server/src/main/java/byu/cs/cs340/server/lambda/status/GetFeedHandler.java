package byu.cs.cs340.server.lambda.status;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.StatusResponse;
import byu.cs.cs340.server.services.LoginServiceImp;
import byu.cs.cs340.server.services.StatusServiceImp;

public class GetFeedHandler implements RequestHandler<StatusRequest, StatusResponse> {

    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        StatusServiceImp service = new StatusServiceImp();
        return service.getAllStatus(request);
    }
}
