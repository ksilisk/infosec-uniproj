package com.ksilisk.infosec.config;

import com.ksilisk.infosec.initialize.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public enum ApplicationProperties implements Initializable {
    INSTANCE;

    private static final Map<String, String> properties = new HashMap<>();

    // properties
    private static final String APPLICATION_RESOURCES_PATH_PROPERTY = "app.resources.path";
    private static final String APPLICATION_USERS_DB_FILENAME_PROPERTY = "app.db.name";
    private static final String APPLICATION_TMP_DIRECTORY_PATH_PROPERTY = "app.resources.tmp.path";
    private static final String APPLICATION_ADMIN_USER_PASSWORD_PROPERTY = "app.users.admin.password";
    private static final String APPLICATION_ADMIN_USER_LOGIN_PROPERTY = "app.users.admin.login";
    private static final String APPLICATION_ADMIN_VIEW_FILENAME_PROPERTY = "app.view.admin.filename";
    private static final String APPLICATION_USER_VIEW_FILENAME_PROPERTY = "app.view.user.filename";
    private static final String APPLICATION_LOGIN_VIEW_FILENAME_PROPERTY = "app.view.login.filename";

    // defaults
    private static final String DEFAULT_APPLICATION_RESOURCES_PATH = getResourcesPath();
    private static final String DEFAULT_APPLICATION_USERS_DB_FILENAME = "infosec.json";
    private static final String DEFAULT_APPLICATION_TMP_DIRECTORY_PATH = "infosec/resources/tmp";
    private static final String DEFAULT_APPLICATION_ADMIN_USER_PASSWORD = "admin";
    private static final String DEFAULT_APPLICATION_ADMIN_USER_LOGIN = "ADMIN";
    private static final String DEFAULT_APPLICATION_ADMIN_VIEW_FILENAME = "admin-view.fxml";
    private static final String DEFAULT_APPLICATION_USER_VIEW_FILENAME = "user-view.fxml";
    private static final String DEFAULT_APPLICATION_LOGIN_VIEW_FILENAME = "login-view.fxml";

    private static String getResourcesPath() {
        URL resourcesURL = ClassLoader.getSystemResource("admin-view.fxml"); // todo found normal path
        if (resourcesURL == null) {
//            log.warn("System resources dir is null"); todo log
        }
        return resourcesURL == null ? "conf" : resourcesURL.getPath();
    }

    public String getApplicationResourcesPath() {
        return properties.getOrDefault(APPLICATION_RESOURCES_PATH_PROPERTY, DEFAULT_APPLICATION_RESOURCES_PATH);
    }

    public String getApplicationUsersDbFilename() {
        return properties.getOrDefault(APPLICATION_USERS_DB_FILENAME_PROPERTY, DEFAULT_APPLICATION_USERS_DB_FILENAME);
    }

    public String getApplicationTmpDirectoryPath() {
        return properties.getOrDefault(APPLICATION_TMP_DIRECTORY_PATH_PROPERTY, DEFAULT_APPLICATION_TMP_DIRECTORY_PATH);
    }

    public String getApplicationAdminUserPassword() {
        return properties.getOrDefault(APPLICATION_ADMIN_USER_PASSWORD_PROPERTY, DEFAULT_APPLICATION_ADMIN_USER_PASSWORD);
    }

    public String getApplicationAdminUserLogin() {
        return properties.getOrDefault(APPLICATION_ADMIN_USER_LOGIN_PROPERTY, DEFAULT_APPLICATION_ADMIN_USER_LOGIN);
    }

    public File getAdminViewFile() {
        String adminViewFileName =
                properties.getOrDefault(APPLICATION_ADMIN_VIEW_FILENAME_PROPERTY, DEFAULT_APPLICATION_ADMIN_VIEW_FILENAME);
        return new File(getResourcesPath(), adminViewFileName);
    }

    public File getUserViewFile() {
        String userViewFileName =
                properties.getOrDefault(APPLICATION_USER_VIEW_FILENAME_PROPERTY, DEFAULT_APPLICATION_USER_VIEW_FILENAME);
        return new File(getResourcesPath(), userViewFileName);
    }

    public File getLoginViewFile() {
        String loginViewFilename =
                properties.getOrDefault(APPLICATION_LOGIN_VIEW_FILENAME_PROPERTY, DEFAULT_APPLICATION_LOGIN_VIEW_FILENAME);
        return new File(getResourcesPath(), loginViewFilename);
    }

    @Override
    public void initialize() throws IOException {

    }
}
