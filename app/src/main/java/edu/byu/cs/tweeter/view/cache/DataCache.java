package edu.byu.cs.tweeter.view.cache;

import byu.cs.cs340.model.domain.User;

public class DataCache {

    private static DataCache instance;

    private User selectedUser = null;

    public static DataCache getInstance() {
        if(instance == null) {
            instance = new DataCache(null);
        }

        return instance;
    }

    public DataCache(User selectedUser) {
        this.selectedUser = selectedUser;
        instance = this;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
        instance = this;

    }

    public User getSelectedUser() {
        return selectedUser;
    }
}
