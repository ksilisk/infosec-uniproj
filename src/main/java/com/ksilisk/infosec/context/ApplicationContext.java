package com.ksilisk.infosec.context;

import com.ksilisk.infosec.entity.User;

public interface ApplicationContext {
    void clear();

    void setCurrentUser(User user);

    User getCurrentUser();
}
