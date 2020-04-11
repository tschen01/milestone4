package edu.byu.cs.tweeter.model.services;


import java.io.IOException;

import byu.cs.cs340.model.services.StatusService;
import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.model.services.response.StatusResponse;
import edu.byu.cs.tweeter.net.ServerFacade;

public class StatusServiceProxy implements StatusService {
    private static final String URL_PATH_PSTATUS = "/getstory";
    private static final String URL_PATH_ASTATUS = "/getfeed";
    private static final String URL_PATH_CSTATUS = "/createstatus";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public StatusResponse getPersonalStatuses(StatusRequest request) throws IOException {
        request.setAuthkey(LoginServiceProxy.getInstance().getAuthToken());
        StatusResponse response = serverFacade.getPersonalStatusResponse(request, URL_PATH_PSTATUS);
        return response;
    }

    @Override
    public StatusResponse getAllStatus(StatusRequest request) throws IOException {
        request.setAuthkey(LoginServiceProxy.getInstance().getAuthToken());
        StatusResponse response = serverFacade.getAllStatusResponse(request, URL_PATH_ASTATUS);
        return response;
    }
    @Override
    public CreateStatusResponse createStatus(CreateStatusRequest request) throws IOException {
        request.setAuthkey(LoginServiceProxy.getInstance().getAuthToken());
        return serverFacade.createStatus(request, URL_PATH_CSTATUS);
    }
}
