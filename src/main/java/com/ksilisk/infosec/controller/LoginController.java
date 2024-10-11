package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.context.ApplicationContext;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.factory.ApplicationStageFactory;
import com.ksilisk.infosec.factory.DefaultApplicationStageFactory;
import com.ksilisk.infosec.repository.DefaultUserRepository;
import com.ksilisk.infosec.repository.UserRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginController implements Initializable {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private UserRepository userRepository;
    private ApplicationProperties applicationProperties;
    private ApplicationStageFactory applicationStageFactory;
    private ApplicationContext applicationContext;

    private final AtomicInteger attempts = new AtomicInteger();

    public void login() {
        try {
            String username = loginField.getText();
            String password = passwordField.getText();
            Optional<User> maybeUser = userRepository.findUser(username);
            if (maybeUser.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "User Not Found. Try again!").show();
                return;
            }

            User user = maybeUser.get();
            if (!user.getPassword().equals(password)) {
                if (attempts.incrementAndGet() >= 4) {
                    exit();
                }
                new Alert(Alert.AlertType.ERROR, "Incorrect Password").show();
                return;
            }

            if (user.getIsBlocked()) {
                new Alert(Alert.AlertType.ERROR, "You are blocked in system. Contact to admin please.").show();
                return;
            }

            applicationContext.setCurrentUser(user);
            if (user.getIsAdmin()) {
                applicationStageFactory.createAdminStage().show();
            } else {
                applicationStageFactory.createChangePasswordStage().show();
            }
            ((Stage) (loginField.getScene().getWindow())).close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }

    public void exit() {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userRepository = DefaultUserRepository.INSTANCE;
        applicationProperties = ApplicationProperties.INSTANCE;
        applicationStageFactory = DefaultApplicationStageFactory.INSTANCE;
        applicationContext = DefaultApplicationContext.INSTANCE;
        loginField.setText(applicationProperties.getApplicationAdminUserLogin());
    }
}