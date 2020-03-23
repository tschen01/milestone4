package byu.cs.cs340.model.services.request;

public class SignUpRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String url;

    public SignUpRequest(){}

    public SignUpRequest(String username, String password ,String firstName, String lastName, String url) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
