package byu.cs.cs340.model.services.request;

public class SignUpRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String url;

    public  SignUpRequest(String username, String password ,String firstName, String lastName, String url) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUrl() {
        return url;
    }
}
