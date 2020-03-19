package byu.cs.cs340.server.dao;

import java.util.ArrayList;
import java.util.List;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.StatusResponse;

public class AllStatusDAO {
    public StatusResponse getAllStatusResponse(StatusRequest request) {

        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
        List<Status> allStatuses = StatusGenerator.generateUsersAndStatus(20);
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


}
