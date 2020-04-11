package byu.cs.cs340.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.List;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.LogoutRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;

public class UserDAO {
    private static AuthkeyDAO authkeyDAO = new AuthkeyDAO();
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    // get user by username
    public User getUser(String username) {
        Table table = dynamoDB.getTable("users");
        Item item = table.getItem("username", username);
        if (item == null) {
            return null;
        }
        return getItemUser(item);
    }
    // login to
    public LoginResponse loginResponse(LoginRequest request) {
        Table table = dynamoDB.getTable("users");
        String username = request.getUsername();
        String password = request.getPassword();

        Item item = table.getItem("username", username);
        // check user exist
        if (item == null) {
            return new LoginResponse(false, "No such user");
        }
        // check user password
        String hashedPassword = (String)item.get("password");
        if (!hashedPassword.equals(MD5.getMd5(password))) {
            return new LoginResponse(false, "Incorrect password");
        }
        // add authkey
        String authkey = authkeyDAO.addAuthkey();
        return new LoginResponse(true, this.getItemUser(item),authkey);
    }
    public SearchUserResponse searchUser(SearchUserRequest alias) {
        return new SearchUserResponse(false);
    }

    public LogoutResponse logout(LogoutRequest request) {
        //  delete authkey
        authkeyDAO.delete(request.getAuthkey());
        return new LogoutResponse(true);
    }

    private User getItemUser(Item item) {
        String imageURL = (String)item.get("image_url");
        String alias = (String)item.get("handle");
        String name = (String)item.get("name");
        String[] names = name.split(" ");
        String firstName = names[0];
        String lastName = names[1];

        return new User(firstName, lastName, alias, imageURL);
    }
}
