package byu.cs.cs340.server.sqs;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import byu.cs.cs340.model.domain.FeedUpdate;
import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.json.Serializer;
import byu.cs.cs340.server.dao.FeedDAO;

public class PostToFeedQueueHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        FeedDAO feedDAO = new FeedDAO();

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            FeedUpdate update = Serializer.deserialize(msg.getBody(), FeedUpdate.class);
            Status post = update.getStatus();
            System.out.println("----------- Recieved array of size:");
            System.out.println(update.getFollower().size());
            List<Item> itemsList = new ArrayList<>();

//            User user = request.getUser();
//            storyDAO.addToStory(user.getAlias(), request.getTimestamp(), request.getMessage(), user.getName(), user.getImageUrl());

            for (int i = 0; i < update.getFollower().size(); i++) {
                User user = update.getFollower().get(i);
                try {
                    Item item = new Item().withPrimaryKey("handle", user.getAlias(), "timestamp", post.getTimestamp())
                            .withString("status_content", post.getContent())
                            .withString("status_name",post.getUser().getName())
                            .withString("image_url", post.getUser().getImageUrl());

                    itemsList.add(item);

                    if(itemsList.size() == 25) {
                        feedDAO.addToFeed(user.getAlias(), post.getTimestamp(), post.getContent(), post.getUser().getAlias(), post.getUser().getName(), post.getUser().getImageUrl());
                        itemsList.clear();
                    }
                    else if (i == update.getFollower().size() - 1){
                        feedDAO.addToFeed(user.getAlias(), post.getTimestamp(), post.getContent(), post.getUser().getAlias(), post.getUser().getName(), post.getUser().getImageUrl());
                        itemsList.clear();
                    }
                }
                catch(Exception x){
                    System.out.println(x.getMessage());
                    throw new RuntimeException("Error while posting statuses to feeds!");
                }
            }
        }

        return null;
    }
}
