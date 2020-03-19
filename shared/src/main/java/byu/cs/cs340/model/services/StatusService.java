package byu.cs.cs340.model.services;

import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.model.services.response.StatusResponse;

public interface StatusService {

    public StatusResponse getPersonalStatuses(StatusRequest request);

    public StatusResponse getAllStatus(StatusRequest request);

    public CreateStatusResponse createStatus(CreateStatusRequest request);

}
