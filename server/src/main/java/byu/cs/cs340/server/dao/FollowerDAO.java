package byu.cs.cs340.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.cs.cs340.model.domain.Follow;
import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.StatusResponse;

public class FollowerDAO {

    private static final String TableName = "followees";

    private static final String HandleAttr = "followee_handle";
    private static final String FollowerHandleAttr = "follower_handle";
    private static final String FollowerNameAttr = "follower_name";
    private static final String ImageAttr = "image_url";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public void createTable() {
        try {
            // Attribute definitions
            ArrayList<AttributeDefinition> tableAttributeDefinitions = new ArrayList<>();

            tableAttributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName("followee_handle")
                    .withAttributeType("S"));
            tableAttributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName("follower_handle")
                    .withAttributeType("S"));

            // Table key schema
            ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
            tableKeySchema.add(new KeySchemaElement()
                    .withAttributeName("followee_handle")
                    .withKeyType(KeyType.HASH));  //Partition key
            tableKeySchema.add(new KeySchemaElement()
                    .withAttributeName("follower_handle")
                    .withKeyType(KeyType.RANGE));  //Sort key

            CreateTableRequest createTableRequest = new CreateTableRequest()
                    .withTableName("visits")
                    .withProvisionedThroughput(new ProvisionedThroughput()
                            .withReadCapacityUnits((long) 1)
                            .withWriteCapacityUnits((long) 1))
                    .withAttributeDefinitions(tableAttributeDefinitions)
                    .withKeySchema(tableKeySchema);

            Table table = dynamoDB.createTable(createTableRequest);
            table.waitForActive();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        try {
            Table table = dynamoDB.getTable(TableName);
            if (table != null) {
                table.delete();
                table.waitForDelete();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteFollower(String followee_handle, String follower_handle) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(HandleAttr, followee_handle,	FollowerHandleAttr, follower_handle);
    }

    public FolloweeResponse getFollowers(FolloweeRequest request) {
        String handle = request.getFollowee().getAlias();
        int pageSize = request.getLimit();
        String lastFollower = request.getLastFollower();
        List<User> users = new ArrayList<>();
        boolean more = false;

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#fol", HandleAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followee_handle", new AttributeValue().withS(handle));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#fol = :followee_handle")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(pageSize);

        if (isNonEmptyString(lastFollower)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(HandleAttr, new AttributeValue().withS(handle));
            startKey.put(FollowerHandleAttr, new AttributeValue().withS(lastFollower));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                String followerHandle = item.get(FollowerHandleAttr).getS();
                String name = item.get(FollowerNameAttr).getS();
                String image = item.get(ImageAttr).getS();
                users.add(getItemUser(name,image,followerHandle));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            lastFollower = lastKey.get(FollowerHandleAttr).getS();
            more = true;
        }
        else{
            lastFollower = null;
        }

        return new FolloweeResponse(users, more, lastFollower);
    }

    private User getItemUser(String name, String imageURL, String alias) {
        String[] names = name.split(" ");
        String firstName = names[0];
        String lastName = names[1];

        return new User(firstName, lastName, alias, imageURL);
    }

    public void addFollower(String followee_handle, String name, String imageURL, String alias) {
        Table table = dynamoDB.getTable(TableName);

        try {
            Item item = new Item()
                    .withPrimaryKey(HandleAttr, followee_handle, FollowerHandleAttr, alias)
                    .with(FollowerNameAttr, name)
                    .with(ImageAttr, imageURL);

            table.putItem(item);
        }
        catch (Exception e) {
            // Record first visit of visitor to location
            e.printStackTrace();
        }
    }

}
