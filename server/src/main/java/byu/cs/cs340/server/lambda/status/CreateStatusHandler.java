package byu.cs.cs340.server.lambda.status;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.server.services.StatusServiceImp;

public class CreateStatusHandler implements RequestHandler<CreateStatusRequest, CreateStatusResponse> {

    @Override
    public CreateStatusResponse handleRequest(CreateStatusRequest request, Context context) {
        StatusServiceImp service = new StatusServiceImp();
        return service.createStatus(request);
    }
}
