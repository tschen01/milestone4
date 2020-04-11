package edu.byu.cs.tweeter.net;

import android.preference.PreferenceActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import byu.cs.cs340.model.services.request.CreateStatusRequest;
import byu.cs.cs340.model.services.request.FollowUnfollowRequest;
import byu.cs.cs340.model.services.request.FolloweeRequest;
import byu.cs.cs340.model.services.request.FollowingRequest;
import byu.cs.cs340.model.services.request.IfFollowingRequest;
import byu.cs.cs340.model.services.request.LoginRequest;
import byu.cs.cs340.model.services.request.LogoutRequest;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.request.SignUpRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.CreateStatusResponse;
import byu.cs.cs340.model.services.response.FollowUnfollowResponse;
import byu.cs.cs340.model.services.response.FolloweeResponse;
import byu.cs.cs340.model.services.response.FollowingResponse;
import byu.cs.cs340.model.services.response.IfFollowingResponse;
import byu.cs.cs340.model.services.response.LoginResponse;
import byu.cs.cs340.model.services.response.LogoutResponse;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.model.services.response.SignUpResponse;
import byu.cs.cs340.model.services.response.StatusResponse;
import edu.byu.cs.tweeter.model.services.LoginServiceProxy;

public class ServerFacade {
    // TODO: Set this the the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://5pmgdic2f5.execute-api.us-west-2.amazonaws.com/tweeter-server";

    public FollowingResponse getFollowees(FollowingRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
    }

    public FolloweeResponse getFollowers(FolloweeRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FolloweeResponse.class);
    }


    public LoginResponse loginResponse(LoginRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
    }

    public SignUpResponse signUpResponse(SignUpRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, SignUpResponse.class);
    }

    public StatusResponse getAllStatusResponse(StatusRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, StatusResponse.class);
    }


    public StatusResponse getPersonalStatusResponse(StatusRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, StatusResponse.class);
    }

    public CreateStatusResponse createStatus(CreateStatusRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", LoginServiceProxy.getInstance().getAuthToken());
        return clientCommunicator.doPost(urlPath, request, header, CreateStatusResponse.class);
    }

    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FollowUnfollowResponse.class);
    }

    public IfFollowingResponse following(IfFollowingRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, IfFollowingResponse.class);
    }

    public SearchUserResponse searchUser(SearchUserRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, SearchUserResponse.class);
    }

    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, LogoutResponse.class);
    }
}
