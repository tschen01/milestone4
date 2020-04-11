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

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;

public class FollowingDAO {
    private static final String TableName = "followings";

    private static final String HandleAttr = "follower_handle";
    private static final String FolloweeHandleAttr = "followee_handle";
    private static final String FolloweeNameAttr = "followee_name";
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
                    .withAttributeName("follower_handle")
                    .withAttributeType("S"));
            tableAttributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName("followee_handle")
                    .withAttributeType("S"));

            // Table key schema
            ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
            tableKeySchema.add(new KeySchemaElement()
                    .withAttributeName("follower_handle")
                    .withKeyType(KeyType.HASH));  //Partition key
            tableKeySchema.add(new KeySchemaElement()
                    .withAttributeName("followee_handle")
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

    public boolean isFollowing(String follower_handle, String followee_handle) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(HandleAttr, follower_handle,  FolloweeHandleAttr, followee_handle);
        if (item == null) {
            return false;
        }
        return true;
    }

    public void deleteFollowing(String follower_handle, String followee_handle) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(HandleAttr, follower_handle, FolloweeHandleAttr, followee_handle);
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        String handle = request.getFollower().getAlias();
        int pageSize = request.getLimit();
        String lastFollower = request.getLastFollowee();
        List<User> users = new ArrayList<>();
        boolean more = false;

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#fol", HandleAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":follower_handle", new AttributeValue().withS(handle));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#fol = :follower_handle")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(pageSize);

        if (isNonEmptyString(lastFollower)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(HandleAttr, new AttributeValue().withS(handle));
            startKey.put(FolloweeHandleAttr, new AttributeValue().withS(lastFollower));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                String followerHandle = item.get(FolloweeHandleAttr).getS();
                String name = item.get(FolloweeNameAttr).getS();
                String image = item.get(ImageAttr).getS();
                users.add(getItemUser(name,image,followerHandle));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            lastFollower = lastKey.get(FolloweeHandleAttr).getS();
            more = true;
        }

        return new FollowingResponse(users, more, lastFollower);
    }

    private User getItemUser(String name, String imageURL, String alias) {
        String[] names = name.split(" ");
        String firstName = names[0];
        String lastName = names[1];

        return new User(firstName, lastName, alias, imageURL);
    }

    public IfFollowingResponse following(IfFollowingRequest request) {
        boolean result;
        try {
            result = isFollowing(request.getFollower_alias(), request.getFollowee_alias());
        } catch (Exception e) {
            e.printStackTrace();
            return new IfFollowingResponse(false);
        }
        return new IfFollowingResponse(true,result);
    }

    public void addFollowing(String follower_handle, String name, String imageURL, String alias) {
        Table table = dynamoDB.getTable(TableName);

        try {
            Item item = new Item()
                    .withPrimaryKey(HandleAttr, follower_handle, FolloweeHandleAttr, alias)
                    .with(FolloweeNameAttr, name)
                    .with(ImageAttr, imageURL);

            table.putItem(item);
        }
        catch (Exception e) {
            // Record first visit of visitor to location
            e.printStackTrace();
        }
    }

}
