package com.ksilisk.infosec.config;

import com.ksilisk.infosec.initialization.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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
    private static final String APPLICATION_CHANGE_PASSWORD_VIEW_FILENAME_PROPERTY = "app.view.user.change-password";
    private static final String APPLICATION_LOGIN_VIEW_FILENAME_PROPERTY = "app.view.login.filename";
    private static final String APPLICATION_DB_PASSWORD_VIEW_FILENAME_PROPERTY = "app.view.db-password.filename";
    private static final String APPLICATION_USER_VIEW_FILENAME_PROPERTY = "app.view.user.filename";
    private static final String APPLICATION_SECURITY_CIPHER_ALGORITHM_PROPERTY = "app.security.cipher.algorithm";
    private static final String APPLICATION_SECURITY_CIPHER_MODE_PROPERTY = "app.security.cipher.mode";
    private static final String APPLICATION_SECURITY_CIPHER_PADDING_PROPERTY = "app.security.cipher.padding";
    private static final String APPLICATION_SECURITY_HASHING_ALGORITHM_PROPERTY = "app.security.hashing.algorithm";
    private static final String APPLICATION_SECURITY_HASHING_SALT_PROPERTY = "app.security.hashing.salt";

    // defaults
    private static final String DEFAULT_APPLICATION_RESOURCES_PATH = "infosec/resources";
    private static final String DEFAULT_APPLICATION_USERS_DB_FILENAME = "infosec.json";
    private static final String DEFAULT_APPLICATION_TMP_DIRECTORY_PATH = "infosec/resources/tmp";
    private static final String DEFAULT_APPLICATION_ADMIN_USER_PASSWORD = "admin";
    private static final String DEFAULT_APPLICATION_ADMIN_USER_LOGIN = "ADMIN";
    private static final String DEFAULT_APPLICATION_ADMIN_VIEW_FILENAME = "admin-view.fxml";
    private static final String DEFAULT_APPLICATION_CHANGE_PASSWORD_VIEW_FILENAME = "change-password-view.fxml";
    private static final String DEFAULT_APPLICATION_DB_PASSWORD_VIEW_FILENAME = "db-password-view.fxml";
    private static final String DEFAULT_APPLICATION_LOGIN_VIEW_FILENAME = "login-view.fxml";
    private static final String DEFAULT_APPLICATION_USER_VIEW_FILENAME = "user-view.fxml";
    private static final String DEFAULT_APPLICATION_SECURITY_CIPHER_ALGORITHM = "AES";
    private static final String DEFAULT_APPLICATION_SECURITY_CIPHER_MODE = "CFB";
    private static final String DEFAULT_APPLICATION_SECURITY_CIPHER_PADDING = "PKCS5Padding";
    private static final String DEFAULT_APPLICATION_SECURITY_HASHING_ALGORITHM = "MD2";

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

    public String getApplicationSecurityCipherAlgorithm() {
        return properties.getOrDefault(APPLICATION_SECURITY_CIPHER_ALGORITHM_PROPERTY, DEFAULT_APPLICATION_SECURITY_CIPHER_ALGORITHM);
    }

    public String getApplicationSecurityCipherMode() {
        return properties.getOrDefault(APPLICATION_SECURITY_CIPHER_MODE_PROPERTY, DEFAULT_APPLICATION_SECURITY_CIPHER_MODE);
    }

    public String getApplicationSecurityCipherPadding() {
        return properties.getOrDefault(APPLICATION_SECURITY_CIPHER_PADDING_PROPERTY, DEFAULT_APPLICATION_SECURITY_CIPHER_PADDING);
    }

    public String getApplicationSecurityHashingAlgorithm() {
        return properties.getOrDefault(APPLICATION_SECURITY_HASHING_ALGORITHM_PROPERTY, DEFAULT_APPLICATION_SECURITY_HASHING_ALGORITHM);
    }

    public String getApplicationSecurityHashingSalt() {
        return properties.get(APPLICATION_SECURITY_HASHING_SALT_PROPERTY);
    }

    public File getAdminViewFile() {
        if (!properties.containsKey(APPLICATION_ADMIN_VIEW_FILENAME_PROPERTY)) {
            return readFromClassPath(DEFAULT_APPLICATION_ADMIN_VIEW_FILENAME);
        }
        String adminViewFileName =
                properties.getOrDefault(APPLICATION_ADMIN_VIEW_FILENAME_PROPERTY, DEFAULT_APPLICATION_ADMIN_VIEW_FILENAME);
        return new File(getApplicationResourcesPath(), adminViewFileName);
    }

    public File getChangePasswordViewFile() {
        if (!properties.containsKey(APPLICATION_CHANGE_PASSWORD_VIEW_FILENAME_PROPERTY)) {
            return readFromClassPath(DEFAULT_APPLICATION_CHANGE_PASSWORD_VIEW_FILENAME);
        }
        String userViewFileName =
                properties.getOrDefault(APPLICATION_CHANGE_PASSWORD_VIEW_FILENAME_PROPERTY, DEFAULT_APPLICATION_CHANGE_PASSWORD_VIEW_FILENAME);
        return new File(getApplicationResourcesPath(), userViewFileName);
    }

    public File getLoginViewFile() {
        if (!properties.containsKey(APPLICATION_LOGIN_VIEW_FILENAME_PROPERTY)) {
            return readFromClassPath(DEFAULT_APPLICATION_LOGIN_VIEW_FILENAME);
        }
        String loginViewFilename =
                properties.getOrDefault(APPLICATION_LOGIN_VIEW_FILENAME_PROPERTY, DEFAULT_APPLICATION_LOGIN_VIEW_FILENAME);
        return new File(getApplicationResourcesPath(), loginViewFilename);
    }

    public File getDBPasswordViewFile() {
        if (!properties.containsKey(APPLICATION_DB_PASSWORD_VIEW_FILENAME_PROPERTY)) {
            return readFromClassPath(DEFAULT_APPLICATION_DB_PASSWORD_VIEW_FILENAME);
        }
        String dbPasswordViewFilename =
                properties.getOrDefault(APPLICATION_DB_PASSWORD_VIEW_FILENAME_PROPERTY, DEFAULT_APPLICATION_DB_PASSWORD_VIEW_FILENAME);
        return new File(getApplicationResourcesPath(), dbPasswordViewFilename);
    }

    public File getUserViewFile() {
        if (!properties.containsKey(APPLICATION_USER_VIEW_FILENAME_PROPERTY)) {
            return readFromClassPath(DEFAULT_APPLICATION_USER_VIEW_FILENAME);
        }
        String userView =
                properties.getOrDefault(APPLICATION_USER_VIEW_FILENAME_PROPERTY, DEFAULT_APPLICATION_USER_VIEW_FILENAME);
        return new File(getApplicationResourcesPath(), userView);
    }

    private File readFromClassPath(String resourceName) {
        try {
            URL resourceURI = ClassLoader.getSystemClassLoader().getResource(resourceName);
            if (resourceURI == null) {
                throw new IllegalStateException("Resource '%s' not found in classpath".formatted(resourceName));
            }
            return new File(resourceURI.toURI());
        } catch (URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void initialize() throws IOException {
        // maybe add initialization properties
    }
}
