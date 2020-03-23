package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;

public class CreateStatusDAOTest {
    private final User user1 = new User("string", "string", "");

    CreateStatusDAO createStatusDAO = new CreateStatusDAO();

    @Test
    void testPost_success() {
        CreateStatusRequest request = new CreateStatusRequest(user1, "", "");
        CreateStatusResponse response = createStatusDAO.createStatus(request);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void testPost_fail() {
        CreateStatusRequest request = new CreateStatusRequest(user1, "tooLong", "");
        CreateStatusResponse response = createStatusDAO.createStatus(request);

        Assertions.assertFalse(response.isSuccess());
    }
}
