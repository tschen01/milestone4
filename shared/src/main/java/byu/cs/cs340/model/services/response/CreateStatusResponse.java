package byu.cs.cs340.model.services.response;


import byu.cs.cs340.model.domain.Status;

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
