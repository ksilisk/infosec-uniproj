package com.ksilisk.infosec.context;

import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.initialize.Initializable;

import java.io.IOException;

public enum DefaultApplicationContext implements ApplicationContext, Initializable {
    INSTANCE;

    private User currentUser;

    @Override
    public void clear() {
        currentUser = null;
    }

    @Override
    public synchronized void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public synchronized User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void initialize() throws IOException {
        // maybe need to initialize
    }
}
