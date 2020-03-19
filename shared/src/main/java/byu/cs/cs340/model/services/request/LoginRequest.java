package byu.cs.cs340.model.services.request;

public class LoginRequest {
    private String firstName;
    private String lastName;

    public  LoginRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return firstName;
    }

    public String getPassword() {
        return lastName;
    }
}
