package edu.byu.cs.tweeter.net.Presenter;
import android.view.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.model.services.SignUpService;
import edu.byu.cs.tweeter.model.services.StatusService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.CreateStatusRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.CreateStatusResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
import edu.byu.cs.tweeter.net.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.SignUpPresenter;
import edu.byu.cs.tweeter.presenter.StatusPresenter;

public class StatusTest {
    private StatusService signUpService = StatusService.getInstance();
    private CreateStatusRequest createStatusRequest;
    private StatusRequest statusRequest;
    private CreateStatusResponse createStatusResponse;
    private StatusResponse statusResponse;

    public class ViewImplementation implements StatusPresenter.View {
    }
    private StatusPresenter presenter = new StatusPresenter(new ViewImplementation()); //TODO: Check this
    @BeforeEach
    void setUp() {
        LoginRequest request = new LoginRequest("Test", "User");
        LoginService.getInstance().login(request);
    }
    @AfterEach
    void cleanUp(){
        createStatusRequest = null;
        statusRequest = null;
        createStatusResponse = null;
        statusResponse = null;
        LoginService.getInstance().logout();
    }
    @Test
    void testGetAllStatusValidUser(){
        statusRequest = new StatusRequest(presenter.getCurrentUser(), 50, null);
        statusResponse = presenter.getAllStatuses(statusRequest);
        Assertions.assertTrue(statusResponse.isSuccess());
        Assertions.assertEquals(50, statusResponse.getStatuses().size());
    }
    @Test
    void testGetAllStatusInvalidUser(){
        statusRequest = new StatusRequest(new User("","",""), 50, null);
        statusResponse = presenter.getAllStatuses(statusRequest);
        Assertions.assertFalse(statusResponse.isSuccess());
        Assertions.assertNull(statusResponse.getStatuses());
    }
    @Test
    void testGetPersonalStatusValidUser(){
        statusRequest = new StatusRequest(presenter.getCurrentUser(), 10, null);
        statusResponse = presenter.getPersonalStatuses(statusRequest);
        Assertions.assertTrue(statusResponse.isSuccess());
        Assertions.assertEquals(10, statusResponse.getStatuses().size());
    }
    @Test
    void testGetPersonalStatusInvalidUser(){
        statusRequest = new StatusRequest(new User("","",""), 50, null);
        statusResponse = presenter.getPersonalStatuses(statusRequest);
        Assertions.assertFalse(statusResponse.hasMorePages());
        Assertions.assertNotNull(statusResponse.getStatuses());
        Assertions.assertEquals(0, statusResponse.getStatuses().size());
    }
    @Test
    void testCreateStatus(){
        createStatusRequest = new CreateStatusRequest(presenter.getCurrentUser(), "it works", null);
        createStatusResponse = presenter.getCreateStatusResponse(createStatusRequest);
        Assertions.assertTrue(createStatusResponse.isSuccess());
        Assertions.assertNotNull(createStatusResponse.getStatus());
    }
}
