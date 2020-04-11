package byu.cs.cs340.model.services.response;


import byu.cs.cs340.model.domain.User;

public class SignUpResponse extends Response {

    private User user;

    private String authkey;

    public SignUpResponse(boolean success) {
        super(success);
    }
    public SignUpResponse(boolean success, User user, String authkey) {
        super(success);
        this.user = user;
        this.authkey = authkey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String getAuthkey() {
        return authkey;
    }
}
