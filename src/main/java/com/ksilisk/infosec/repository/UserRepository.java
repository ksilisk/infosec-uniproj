package com.ksilisk.infosec.repository;

import com.ksilisk.infosec.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findUser(String username);

    void saveUser(User user) throws IOException;

    List<User> findAllUsers();

    void saveAllUsers(List<User> users);
}
