package byu.cs.cs340.server.services;



import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.serialize.Serializer;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.StatusService;
import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.model.services.response.StatusResponse;
import byu.cs.cs340.server.dao.FeedDAO;
import byu.cs.cs340.server.dao.CreateStatusDAO;
import byu.cs.cs340.server.dao.StoryDAO;

public class StatusServiceImp implements StatusService {

    @Override
    public StatusResponse getPersonalStatuses(StatusRequest request) {
        StoryDAO dao = new StoryDAO();
        return dao.getStory(request.getUser().getAlias(), request.getLimit(), request.getLastStatus());
    }

    @Override
    public StatusResponse getAllStatus(StatusRequest request) {
        FeedDAO dao = new FeedDAO();
        return dao.getFeed(request.getUser().getAlias(), request.getLimit(), request.getLastStatus());
    }

    @Override
    public CreateStatusResponse createStatus(CreateStatusRequest request) {
//        CreateStatusDAO dao = new CreateStatusDAO();
//        return dao.createStatus(request);

        try {
            StoryDAO storyDAO = new StoryDAO();
            User user = request.getUser();
            storyDAO.addToStory(user.getAlias(), request.getTimestamp(), request.getMessage(), user.getName(), user.getImageUrl());

            String queueURL = "https://sqs.us-west-2.amazonaws.com/849364575501/GetPostFollowersQueue";
            String messageBody = new Gson().toJson(request);
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueURL)
                    .withMessageBody(messageBody)
                    .withDelaySeconds(5);
            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
            String msgId = send_msg_result.getMessageId();

            System.out.println("Message ID: " + msgId);
            return new CreateStatusResponse(true);
        }
        catch (Exception e){
            e.printStackTrace();
            return new CreateStatusResponse(false);
        }
    }

}
