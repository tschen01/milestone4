package byu.cs.cs340.model.services.response;

import java.util.List;

import byu.cs.cs340.model.domain.Status;

public class StatusResponse extends PagedResponse {
    private List<Status> statuses;

    public StatusResponse(String message) {
        super(false, message, false);
    }

    public StatusResponse(List<Status> statuses, boolean hasMorePages, String lastObj) {
        super(true, hasMorePages, lastObj);
        this.statuses = statuses;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
