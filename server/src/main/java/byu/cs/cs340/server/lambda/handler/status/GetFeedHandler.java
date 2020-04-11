package byu.cs.cs340.server.lambda.handler.status;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.StatusResponse;
import byu.cs.cs340.server.dao.AuthkeyDAO;
import byu.cs.cs340.server.services.AuthkeyService;
import byu.cs.cs340.server.services.StatusServiceImp;

public class GetFeedHandler implements RequestHandler<StatusRequest, StatusResponse> {

    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        if (!verifyAuthkey(request.getAuthkey())) {
            throw new RuntimeException("[500Error]: wrong auth key");
        }

        StatusServiceImp service = new StatusServiceImp();
        StatusResponse response = service.getAllStatus(request);
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
