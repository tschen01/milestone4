package edu.byu.cs.tweeter.presenter;

import byu.cs.cs340.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginServiceProxy;

public abstract class Presenter {

    public User getCurrentUser() {
        return LoginServiceProxy.getInstance().getCurrentUser();
    }

}
