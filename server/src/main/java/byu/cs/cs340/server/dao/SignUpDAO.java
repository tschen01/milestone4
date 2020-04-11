package byu.cs.cs340.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.response.SignUpResponse;

public class SignUpDAO {
    private static AuthkeyDAO authkeyDAO = new AuthkeyDAO();
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public SignUpResponse signUpResponse(SignUpRequest request) {


        if (request.getUsername().equals("invalid") || request.getPassword().equals("invalid")) {
            return new SignUpResponse(false);
        }

        User user = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);

        return new SignUpResponse(true, user, "authkey");
    }
}
