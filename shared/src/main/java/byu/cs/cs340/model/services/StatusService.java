package byu.cs.cs340.model.services;

import java.io.IOException;

import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.model.services.response.StatusResponse;

public interface StatusService {

    public StatusResponse getPersonalStatuses(StatusRequest request) throws IOException;

    public StatusResponse getAllStatus(StatusRequest request) throws IOException;

    public CreateStatusResponse createStatus(CreateStatusRequest request) throws IOException;

}
