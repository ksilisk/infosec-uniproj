package com.ksilisk.infosec.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.database.DatabaseClient;
import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.initialize.Initializable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DefaultUserRepository implements UserRepository, Initializable {
    INSTANCE;

    private final ApplicationProperties appProperties = ApplicationProperties.INSTANCE;
    private final DatabaseClient databaseClient = DatabaseClient.INSTANCE;
    private final Map<String, User> users = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void initialize() throws IOException {
        byte[] databaseData = databaseClient.load();
        if (databaseData.length > 0) {
            List<User> userList = objectMapper.readValue(databaseClient.load(), new TypeReference<>() {
            });
            this.users.putAll(userList.stream().collect(Collectors.toMap(User::username, Function.identity())));
        }

        if (!users.containsKey(appProperties.getApplicationAdminUserLogin())) {
            User adminUser = new User(appProperties.getApplicationAdminUserLogin(),
                    appProperties.getApplicationAdminUserPassword(), false, null, true);
            saveUser(adminUser);
        }
    }

    @Override
    public Optional<User> findUser(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public void saveUser(User user) throws IOException {
        users.put(user.username(), user);
        databaseClient.save(objectMapper.writeValueAsBytes(user));
    }
}
