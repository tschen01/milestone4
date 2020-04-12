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

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.StatusResponse;

public class StoryDAO {
    private static final String TableName = "story";

    private static final String HandleAttr = "handle";
    private static final String TimestampAttr = "timestamp";
    private static final String ContentAttr = "status_content";
    private static final String NameAttr = "status_name";
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
                    .withAttributeName("handle")
                    .withAttributeType("S"));
            tableAttributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName("timestamp")
                    .withAttributeType("S"));

            // Table key schema
            ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
            tableKeySchema.add(new KeySchemaElement()
                    .withAttributeName("handle")
                    .withKeyType(KeyType.HASH));  //Partition key
            tableKeySchema.add(new KeySchemaElement()
                    .withAttributeName("timestamp")
                    .withKeyType(KeyType.RANGE));  //Sort key

            CreateTableRequest createTableRequest = new CreateTableRequest()
                    .withTableName("feed")
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

    public void addToStory(String handle, String time, String content, String name, String image_url) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(HandleAttr, handle, TimestampAttr, time)
                .with(NameAttr, name)
                .with(ContentAttr, content)
                .with(ImageAttr, image_url);

        table.putItem(item);
    }


    public void deleteFromFeed(String handle, String time) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(HandleAttr, handle, TimestampAttr, time);
    }

    public StatusResponse getStory(String handle, int pageSize, String lastTimestamp) {
        List<Status> statuses = new ArrayList<>();
        boolean more = false;

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#han", HandleAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":handle", new AttributeValue().withS(handle));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#han = :handle")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(pageSize);

        if (isNonEmptyString(lastTimestamp)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(HandleAttr, new AttributeValue().withS(handle));
            startKey.put(TimestampAttr, new AttributeValue().withS(lastTimestamp));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                String timestamp = item.get(TimestampAttr).getS();
                String content = item.get(ContentAttr).getS();
                String name = item.get(NameAttr).getS();
                String image = item.get(ImageAttr).getS();
                statuses.add(getStatus(name,image,handle,content,timestamp));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            lastTimestamp = lastKey.get(TimestampAttr).getS();
            more = true;
        }

        return new StatusResponse(statuses, more, lastTimestamp);
    }

    private Status getStatus(String name, String image, String handle, String content, String timestamp) {

        User user = getItemUser(name, image, handle);

        return new Status(user, content, timestamp);
    }

    private User getItemUser(String name, String imageURL, String alias) {
        String[] names = name.split(" ");
        String firstName = names[0];
        String lastName = names[1];

        return new User(firstName, lastName, alias, imageURL);
    }
}
