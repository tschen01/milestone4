package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.StatusService;
import edu.byu.cs.tweeter.net.request.CreateStatusRequest;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.CreateStatusResponse;
import edu.byu.cs.tweeter.net.response.StatusResponse;

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

    public StatusResponse getAllStatuses(StatusRequest request) {
        return StatusService.getInstance().getAllStatus(request);
    }

    public StatusResponse getPersonalStatuses(StatusRequest request) {
        return StatusService.getInstance().getPersonalStatuses(request);
    }

    public CreateStatusResponse getCreateStatusResponse(CreateStatusRequest request) {
        return StatusService.getInstance().createStatus(request);
    }


}
