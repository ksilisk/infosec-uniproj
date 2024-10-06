package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.repository.DefaultUserRepository;
import com.ksilisk.infosec.repository.UserRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private UserRepository userRepository;
    private ApplicationProperties applicationProperties;

    public void login(MouseEvent event) throws IOException {
        String username = loginField.getText();
        String password = passwordField.getText();
        Optional<User> maybeUser = userRepository.findUser(username);
        if (maybeUser.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "User Not Found. Try again!").show();
            return;
        }

        User user = maybeUser.get();
        if (!user.password().equals(password)) {
            new Alert(Alert.AlertType.ERROR, "Incorrect Password").show();
            return;
        }

        if (user.isAdmin()) {
            FXMLLoader adminLoader = new FXMLLoader(applicationProperties.getAdminViewFile().toURI().toURL());
            Parent parent = adminLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setOnCloseRequest(event1 -> DefaultApplicationContext.INSTANCE.clear());
            stage.show();
        } else {
            FXMLLoader adminLoader = new FXMLLoader(applicationProperties.getUserViewFile().toURI().toURL());
            Parent parent = adminLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setOnCloseRequest(event1 -> DefaultApplicationContext.INSTANCE.clear());
            stage.show();
        }
    }

    public void exit(MouseEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userRepository = DefaultUserRepository.INSTANCE;
        applicationProperties = ApplicationProperties.INSTANCE;
        loginField.setText(applicationProperties.getApplicationAdminUserLogin());
    }
}