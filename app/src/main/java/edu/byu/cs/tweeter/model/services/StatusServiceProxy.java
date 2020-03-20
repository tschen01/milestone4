package edu.byu.cs.tweeter.model.services;


import java.io.IOException;

import byu.cs.cs340.model.services.StatusService;
import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.model.services.response.StatusResponse;
import edu.byu.cs.tweeter.net.ServerFacade;

public class StatusServiceProxy implements StatusService {
    private static final String URL_PATH_PSTATUS = "/getpersonalstatus";
    private static final String URL_PATH_ASTATUS = "/getallstatus";
    private static final String URL_PATH_CSTATUS = "/createstatus";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public StatusResponse getPersonalStatuses(StatusRequest request) throws IOException {
        return serverFacade.getPersonalStatusResponse(request, URL_PATH_PSTATUS);
    }

    @Override
    public StatusResponse getAllStatus(StatusRequest request) throws IOException {
        return serverFacade.getAllStatusResponse(request, URL_PATH_ASTATUS);
    }
    @Override
    public CreateStatusResponse createStatus(CreateStatusRequest request) throws IOException {
        return serverFacade.createStatus(request, URL_PATH_CSTATUS);
    }
}
