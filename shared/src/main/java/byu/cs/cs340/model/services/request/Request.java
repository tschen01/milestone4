package byu.cs.cs340.model.services.request;

public class Request {
    private String authkey;

    public  Request() {}

    public Request(String authkey) {
        this.authkey = authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String getAuthkey() {
        return authkey;
    }
}
