package byu.cs.cs340.model.services.request;

public class LoginRequest {
    private String firstName;
    private String lastName;

    public LoginRequest() {}

    public LoginRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
