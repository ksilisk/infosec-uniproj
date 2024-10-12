package com.ksilisk.infosec.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.database.DatabaseClient;
import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.initialization.Initializable;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DefaultUserRepository implements UserRepository, Initializable, Closeable {
    INSTANCE;

    private final ApplicationProperties appProperties = ApplicationProperties.INSTANCE;
    private final DatabaseClient databaseClient = DatabaseClient.INSTANCE;
    private final Map<String, User> users = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void initialize() throws IOException {
        // maybe add initialize
    }

    @Override
    public Optional<User> findUser(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public void saveUser(User user) throws IOException {
        users.put(user.getUsername(), user);
        databaseClient.writeAheadLog(objectMapper.writeValueAsBytes(user));
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void saveAllUsers(List<User> users) throws IOException {
        for (User user : users) {
            saveUser(user);
        }
    }

    @Override
    public void load() throws Exception {
        byte[] databaseData = databaseClient.load();
        if (databaseData.length > 0) {
            List<User> userList = objectMapper.readValue(databaseData, new TypeReference<>() {
            });
            this.users.putAll(userList.stream().collect(Collectors.toMap(User::getUsername, Function.identity())));
        }
        if (!users.containsKey(appProperties.getApplicationAdminUserLogin())) {
            User adminUser = new User(appProperties.getApplicationAdminUserLogin(),
                    appProperties.getApplicationAdminUserPassword(), false, false, true);
            saveUser(adminUser);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            List<User> userList = users.values().stream().toList();
            databaseClient.save(objectMapper.writeValueAsBytes(userList));
        } catch (IOException ioException) {
            throw ioException;
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new IllegalStateException(ex);
        }
    }
}
