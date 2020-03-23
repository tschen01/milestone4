package byu.cs.cs340.model.services.request;

public class SearchUserRequest {
    String alias;

    public SearchUserRequest() {}

    public SearchUserRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
