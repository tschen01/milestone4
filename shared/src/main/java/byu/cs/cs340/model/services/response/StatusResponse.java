package byu.cs.cs340.model.services.response;

import java.util.List;

import byu.cs.cs340.model.domain.Status;

public class StatusResponse extends PagedResponse {
    private List<Status> statuses;

    public StatusResponse(Boolean success) {
        super(success, false);
    }

    public StatusResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
