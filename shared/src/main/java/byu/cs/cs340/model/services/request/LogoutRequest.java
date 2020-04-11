package byu.cs.cs340.model.services.request;

public class LogoutRequest {
    String authkey;
    public LogoutRequest(String authkey) {
        this.authkey = authkey;
    }
    public LogoutRequest() { }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String getAuthkey() {
        return authkey;
    }
}
