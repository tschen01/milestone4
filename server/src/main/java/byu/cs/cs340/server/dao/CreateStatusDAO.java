package byu.cs.cs340.server.dao;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;

public class CreateStatusDAO {
    public CreateStatusResponse createStatus(CreateStatusRequest request) {
        Status newStatus = new Status(request.getUser(), request.getMessage(), request.getTimestamp());

        StatusGenerator.addStatus(newStatus);

        return new CreateStatusResponse(true, newStatus);
    }

}
