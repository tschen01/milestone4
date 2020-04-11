package byu.cs.cs340.model.services.response;


import byu.cs.cs340.model.domain.User;

public class LoginResponse extends Response {

    private User user;

    private String authkey;

    public  LoginResponse(boolean success, String message) {
        super(success, message);
    }
    public  LoginResponse(boolean success, User user, String authkey) {
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

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }
}
