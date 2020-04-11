package byu.cs.cs340.server.lambda.handler.status;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.server.services.StatusServiceImp;

public class CreateStatusHandler implements RequestHandler<CreateStatusRequest, CreateStatusResponse> {

    @Override
    public CreateStatusResponse handleRequest(CreateStatusRequest request, Context context) {
        if (request.getAuthkey() == null || !request.getAuthkey().equals("authkey")) {
            throw new RuntimeException("[500Error]: wrong auth key");
        }

        StatusServiceImp service = new StatusServiceImp();
        CreateStatusResponse response = service.createStatus(request);
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
