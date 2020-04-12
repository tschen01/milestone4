package byu.cs.cs340.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;

import java.io.IOException;
import java.util.List;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;

public class CreateStatusDAO {

//    private DynamoDB dynamoDB;
//    private AmazonDynamoDB client;
//    private Table table;
    private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    private DynamoDB dynamoDB = new DynamoDB(client);
    private Table table = dynamoDB.getTable("feed");
    public CreateStatusResponse createStatus(List<Item> batch) throws IOException {

//        if (request.getMessage().equals("tooLong")) {
//            return new CreateStatusResponse(false);
//        }
//        Status newStatus = new Status(request.getUser(), request.getMessage(), request.getTimestamp());
//
//        return new CreateStatusResponse(true, newStatus);

        try {
            TableWriteItems tableWriteItems = new TableWriteItems("feed");
            tableWriteItems.withItemsToPut(batch);
            BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
            while(outcome.getUnprocessedItems().size() > 0) {
                outcome = dynamoDB.batchWriteItemUnprocessed(outcome.getUnprocessedItems());
            }
        }
        catch (Exception e) {
            System.err.println("Unable to add item");
            System.err.println(e.getMessage());
            throw new IOException("Error when posting status to user feed");
        }
    }

}
