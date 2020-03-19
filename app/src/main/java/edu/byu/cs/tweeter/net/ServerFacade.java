package edu.byu.cs.tweeter.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.net.request.CreateStatusRequest;
import edu.byu.cs.tweeter.net.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.net.request.FolloweeRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.CreateStatusResponse;
import edu.byu.cs.tweeter.net.response.FollowUnfollowResponse;
import edu.byu.cs.tweeter.net.response.FolloweeResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.LogoutResponse;
import edu.byu.cs.tweeter.net.response.SearchUserResponse;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
import edu.byu.cs.tweeter.net.response.StatusResponse;

public class ServerFacade {

    private static Map<User, List<User>> followeesByFollower;

    public FollowingResponse getFollowees(FollowingRequest request) {

        if(request.getFollower() == null) {
            return new FollowingResponse(false);
        }

        if (followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }

        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        List<User> allFollowees = followeesByFollower.get(request.getFollower());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Generates the followee data.
     */
    private Map<User, List<User>> initializeFollowees() {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        List<Follow> follows = getFollowGenerator().sortFollowArray(getFollowGenerator().getAllFollows(), FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        return followeesByFollower;
    }

    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }


    private static Map<User, List<User>> followersByFollowee;

    public FolloweeResponse getFollowers(FolloweeRequest request) {

        if(request.getFollower() == null) {
            return new FolloweeResponse(false);
        }

        if (followersByFollowee == null) {
            followersByFollowee = initializeFollowers();
        }

        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        List<User> allFollowers = followersByFollowee.get(request.getFollower());
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowers != null) {
                int followeesIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

                for(int limitCounter = 0; followeesIndex < allFollowers.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowers.size();
            }
        }

        return new FolloweeResponse(responseFollowers, hasMorePages);
    }

    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followeesIndex = 0;

        if(lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Generates the follower data.
     */
    private Map<User, List<User>> initializeFollowers() {

        Map<User, List<User>> followersByFollowee = new HashMap<>();

        List<Follow> follows = getFollowGenerator().sortFollowArray(getFollowGenerator().getAllFollows(), FollowGenerator.Sort.FOLLOWEE_FOLLOWER);

        // Populate a map of followers, keyed by followee so we can easily handle follower requests
        for(Follow follow : follows) {
            List<User> followers = followersByFollowee.get(follow.getFollowee());

            if(followers == null) {
                followers = new ArrayList<>();
                followersByFollowee.put(follow.getFollowee(), followers);
            }

            followers.add(follow.getFollower());
        }

        return followersByFollowee;
    }


    public LoginResponse loginResponse(LoginRequest request) {
        if (request.getUsername().equals("invalid") || request.getPassword().equals("invalid")) {
            return new LoginResponse(false);
        }
        User user = new User(request.getUsername(),request.getPassword(),
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        for (int i = 0; i < 20; i++) {
            StatusGenerator.getInstance().generateStatus(user);
        }
        return new LoginResponse(true, user);
    }

    public SignUpResponse signUpResponse(SignUpRequest request) {
        if (request.getUsername().equals("invalid") || request.getPassword().equals("invalid")) {
            return new SignUpResponse(false);
        }

        String url = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        User user = new User(request.getFirstName(),request.getLastName(), url);

        for (int i = 0; i < 10; i++) {
            StatusGenerator.getInstance().generateStatus(user);
        }

        return new SignUpResponse(true, user);
    }

    public StatusResponse getAllStatusResponse(StatusRequest request) {

        if (!followeesByFollower.containsKey(request.getUser())) {
            return new StatusResponse(false);
        }

        List<User> allFollowees = followeesByFollower.get(request.getUser());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        List<Status> allStatuses = new ArrayList<>();
        for (User followee: allFollowees) {
            allStatuses.addAll(getStatusGenerator().getPersonalStatus(followee));
        }
        sortStatus(allStatuses);

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allStatuses.size() != 0) {
                int statuesesIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);

                for(int limitCounter = 0; statuesesIndex < allFollowees.size() && limitCounter < request.getLimit(); statuesesIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statuesesIndex));
                }

                hasMorePages = statuesesIndex < allStatuses.size();
            }
        }

        return new StatusResponse(responseStatuses, hasMorePages);
    }

    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int statusIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                }
            }
        }
        return statusIndex;
    }

    public StatusResponse getPersonalStatusResponse(StatusRequest request) {

        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
        List<Status> allStatuses = getStatusGenerator().getPersonalStatus(request.getUser());
        sortStatus(allStatuses);

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allStatuses != null) {
                int statuesesIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);

                for(int limitCounter = 0; statuesesIndex < allStatuses.size() && limitCounter < request.getLimit(); statuesesIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statuesesIndex));
                }

                hasMorePages = statuesesIndex < allStatuses.size();
            }
        }

        return new StatusResponse(responseStatuses, hasMorePages);
    }

    private void sortStatus(List<Status> statuses) {
        Collections.sort(statuses, new Comparator<Status>() {
            @Override
            public int compare(Status status1, Status status2) {
                int result = status2.getTimestamp().compareTo(
                        status1.getTimestamp());

                if(result == 0) {
                    return status2.getTimestamp().compareTo(
                            status1.getTimestamp());
                }
                return result;
            }
        });

    }

    private StatusGenerator getStatusGenerator() {
        return StatusGenerator.getInstance();
    }

    public void setData(User user) {
        getFollowGenerator().generateUsersAndFollows(50,
                0, 10, FollowGenerator.Sort.FOLLOWER_FOLLOWEE, user);
        followeesByFollower = initializeFollowees();
        followersByFollowee = initializeFollowers();

        followeesByFollower.get(user).add(new User("Test", "User", ""));
    }

    public LogoutResponse logout() {
        followeesByFollower = null;
        followersByFollowee = null;
        getFollowGenerator().clearAllFollows();
        getStatusGenerator().clearAllStatus();
        UserGenerator.getInstance().clearInstance();
        return new LogoutResponse(true);
    }

    public CreateStatusResponse createStatus(CreateStatusRequest request) {
        Status newStatus = new Status(request.getUser(), request.getMessage(), request.getTimestamp());

        getStatusGenerator().addStatus(newStatus);

        return new CreateStatusResponse(true, newStatus);
    }

    public FollowUnfollowResponse followUnfollow(FollowUnfollowRequest request) {

        User follower = request.getFollow().getFollower();
        if (follower != LoginService.getInstance().getCurrentUser()) {
            return new FollowUnfollowResponse(false, "wrong user");
        }
        User followee = request.getFollow().getFollowee();
        List<User> allFollowees = followeesByFollower.get(follower);

        if (allFollowees.contains(followee)) {
            getFollowGenerator().deleteFollow(request.getFollow());
            followeesByFollower.get(follower).remove(followee);
            if (followersByFollowee.containsKey(followee)) {
                followersByFollowee.get(followee).remove(follower);
            }
            return new FollowUnfollowResponse(true, true);
        }
        else if (!allFollowees.contains(followee)) {
            getFollowGenerator().addFollow(request.getFollow());
            followeesByFollower.get(follower).add(followee);
            if (followersByFollowee.containsKey(followee)) {
                followersByFollowee.get(followee).add(follower);
            }
            else {
                List<User> followers = new ArrayList<>();
                followers.add(follower);
                followersByFollowee.put(followee, followers);
            }
            return new FollowUnfollowResponse(true, false);
        }

        return new FollowUnfollowResponse(false);
    }

    public Boolean following(User user) {
        List<User> users = followeesByFollower.get(LoginService.getInstance().getCurrentUser());
        return users.contains(user);
    }

    public SearchUserResponse searchUser(String alias) {

        List<User> users = UserGenerator.getInstance().getUsers();
        users.add(LoginService.getInstance().getCurrentUser());

        for (User user : users) {
            if (user.getAlias().equals(alias)) {
                return new SearchUserResponse(true, user);
            }
        }

        return new SearchUserResponse(false);
    }

}
