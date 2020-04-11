package byu.cs.cs340.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;

import java.sql.Timestamp;
import java.util.*;


public class AuthkeyDAO {

    private static final String TableName = "authkey";

    private static final String AuthkeyAttr = "authkey";
    private static final String TimestampAttr = "timestamp";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public Boolean verifyAuthkey(String authkey) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AuthkeyAttr, authkey);

        if (item != null) {
            return true;
        }
        return false;
    }

    public String addAuthkey() {
        UUID gfg1 = UUID.randomUUID();
        String authkey = gfg1.toString();
        Table table = dynamoDB.getTable(TableName);
        String t = new Timestamp(System.currentTimeMillis()).toString();

        Item item = new Item()
                .withPrimaryKey(AuthkeyAttr, authkey)
                .with(TimestampAttr, t);

        table.putItem(item);

        return authkey;
    }

    public void delete(String authkey) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(AuthkeyAttr, authkey);
    }

}
