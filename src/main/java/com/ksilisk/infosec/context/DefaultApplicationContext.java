package com.ksilisk.infosec.context;

import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.initialization.Initializable;

import java.io.IOException;

public enum DefaultApplicationContext implements ApplicationContext, Initializable {
    INSTANCE;

    private volatile User currentUser;

    @Override
    public void clear() {
        currentUser = null;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void initialize() throws IOException {
        // maybe need to initialize
    }
}
