package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.server.lambda.handler.status.CreateStatusHandler;

public class CreateStatusHandlerTest {
    private final User user1 = new User("User11", "11", "");

    CreateStatusHandler createStatusHandler = new CreateStatusHandler();
    CreateStatusDAO createStatusDAO = new CreateStatusDAO();

    @Test
    void testPost_success() {
        CreateStatusRequest request = new CreateStatusRequest(user1, "lets go", "2012");
        request.setAuthkey("authkey");
        CreateStatusResponse response = createStatusHandler.handleRequest(request, null);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void testPost_fail() {
        CreateStatusRequest request = new CreateStatusRequest(user1, "tooLong", "");
        request.setAuthkey("authkey");
        try {
            CreateStatusResponse response = createStatusHandler.handleRequest(request, null);
        } catch (Exception e){
            Assertions.assertEquals("[400Error]", e.getMessage());
        }
        CreateStatusRequest request2 = new CreateStatusRequest(user1, "tooLong", "");
        request2.setAuthkey("noauthkey");
        try {
            CreateStatusResponse response = createStatusHandler.handleRequest(request2, null);
        } catch (Exception e){
            Assertions.assertEquals("[500Error]: wrong auth key", e.getMessage());
        }
    }
}
