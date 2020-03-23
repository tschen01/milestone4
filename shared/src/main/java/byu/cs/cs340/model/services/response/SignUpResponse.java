package byu.cs.cs340.model.services.response;


import byu.cs.cs340.model.domain.User;

public class SignUpResponse extends Response {

    private User user;

    public SignUpResponse(boolean success) {
        super(success);
    }
    public SignUpResponse(boolean success, User user) {
        super(success);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
