package byu.cs.cs340.model.services.response;

public class LogoutResponse extends Response {

    public LogoutResponse(boolean success) {
        super(success);
    }
    public LogoutResponse(boolean success, String error) {
        super(success, error);
    }

}
