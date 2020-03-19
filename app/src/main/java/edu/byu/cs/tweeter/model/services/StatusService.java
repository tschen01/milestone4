package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.CreateStatusRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.CreateStatusResponse;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
import edu.byu.cs.tweeter.net.response.StatusResponse;

public class StatusService {
    private static StatusService instance;

    private final ServerFacade serverFacade;

    public static StatusService getInstance() {
        if(instance == null) {
            instance = new StatusService();
        }

        return instance;
    }

    private StatusService() {
        serverFacade = new ServerFacade();
    }

    public StatusResponse getPersonalStatuses(StatusRequest request) {
        StatusResponse personalStatuses = serverFacade.getPersonalStatusResponse(request);
        return personalStatuses;
    }

    public StatusResponse getAllStatus(StatusRequest request) {
        return serverFacade.getAllStatusResponse(request);
    }

    public CreateStatusResponse createStatus(CreateStatusRequest request) {
        return serverFacade.createStatus(request);
    }

}
