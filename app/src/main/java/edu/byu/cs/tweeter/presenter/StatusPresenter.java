package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.model.services.response.StatusResponse;
import edu.byu.cs.tweeter.model.services.StatusServiceProxy;

public class StatusPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }
    public StatusPresenter(View view) {
        this.view = view;
    }

    public StatusResponse getAllStatuses(StatusRequest request) throws IOException {
        StatusServiceProxy serviceProxy = new StatusServiceProxy();
        return serviceProxy.getAllStatus(request);
    }

    public StatusResponse getPersonalStatuses(StatusRequest request) throws IOException {
        StatusServiceProxy serviceProxy = new StatusServiceProxy();
        return serviceProxy.getPersonalStatuses(request);
    }

    public CreateStatusResponse getCreateStatusResponse(CreateStatusRequest request) throws IOException {
        StatusServiceProxy serviceProxy = new StatusServiceProxy();
        return serviceProxy.createStatus(request);
    }


}
