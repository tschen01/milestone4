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
import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.model.services.response.SignUpResponse;

public class UserDAO {
    private static final String TableName = "users";

    private static final String UsernameAttr = "username";
    private static final String HandleAttr = "handle";
    private static final String ImageAttr = "image_url";
    private static final String NameAttr = "name";
    private static final String PasswordAttr = "password";

    private static AuthkeyDAO authkeyDAO = new AuthkeyDAO();
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    // get user by username
    public User getUser(String username) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(UsernameAttr, username);
        if (item == null) {
            return null;
        }
        return getItemUser(item);
    }
    // login to
    public LoginResponse loginResponse(LoginRequest request) {
        Table table = dynamoDB.getTable(TableName);
        String username = request.getUsername();
        String password = request.getPassword();

        Item item = table.getItem(UsernameAttr, username);
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
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(UsernameAttr, alias.getAlias().substring(1));
        // check user exist
        if (item == null) {
            return new SearchUserResponse(false);
        }

        return new SearchUserResponse(true, getItemUser(item));
    }

    public LogoutResponse logout(LogoutRequest request) {
        //  delete authkey
        authkeyDAO.delete(request.getAuthkey());
        return new LogoutResponse(true);
    }

    private User getItemUser(Item item) {
        String imageURL = (String)item.get(ImageAttr);
        String alias = (String)item.get(HandleAttr);
        String name = (String)item.get(NameAttr);
        String[] names = name.split(" ");
        String firstName = names[0];
        String lastName = names[1];

        return new User(firstName, lastName, alias, imageURL);
    }

    public SignUpResponse signUpResponse(SignUpRequest request) {
        Table table = dynamoDB.getTable(TableName);
        String username = request.getUsername();
        String alias = "@" + request.getUsername();
        String password = request.getPassword();
        String image = request.getUrl();
        String name = request.getFirstName() + " " + request.getLastName();
        Item item;

        try {
            item = table.getItem(UsernameAttr, username);
            // check user exist
            if (item != null) {
                throw new RuntimeException("user exist");
            }

            item = new Item()
                    .withPrimaryKey(UsernameAttr, username)
                    .with(HandleAttr, alias)
                    .with(ImageAttr, image)
                    .with(NameAttr, name)
                    .with(PasswordAttr, password)
            ;
            table.putItem(item);
        } catch (Exception e) {
            return new SignUpResponse(false);
        }

        // add authkey
        String authkey = authkeyDAO.addAuthkey();
        return new SignUpResponse(true, getItemUser(item), authkey);
    }
}
