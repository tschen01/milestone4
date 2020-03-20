package byu.cs.cs340.server.services;

import byu.cs.cs340.model.services.StatusService;
import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.model.services.response.StatusResponse;
import byu.cs.cs340.server.dao.FeedDAO;
import byu.cs.cs340.server.dao.CreateStatusDAO;
import byu.cs.cs340.server.dao.StoryDAO;

public class StatusServiceImp implements StatusService {

    @Override
    public StatusResponse getPersonalStatuses(StatusRequest request) {
        StoryDAO dao = new StoryDAO();
        return dao.getPersonalStatusResponse(request);
    }

    @Override
    public StatusResponse getAllStatus(StatusRequest request) {
        FeedDAO dao = new FeedDAO();
        return dao.getAllStatusResponse(request);
    }

    @Override
    public CreateStatusResponse createStatus(CreateStatusRequest request) {
        CreateStatusDAO dao = new CreateStatusDAO();
        return dao.createStatus(request);
    }
}
