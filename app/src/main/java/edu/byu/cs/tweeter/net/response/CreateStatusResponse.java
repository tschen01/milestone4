package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.Status;

public class CreateStatusResponse extends Response {

    Status status;

    public CreateStatusResponse(boolean success) {
        super(success);
    }
    public CreateStatusResponse(boolean success, Status status) {
        super(success);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
