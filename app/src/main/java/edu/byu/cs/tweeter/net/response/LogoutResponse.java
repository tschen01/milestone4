package edu.byu.cs.tweeter.net.response;

public class LogoutResponse extends Response {

    public LogoutResponse(boolean success) {
        super(success);
    }
    public LogoutResponse(boolean success, String error) {
        super(success, error);
    }

}
