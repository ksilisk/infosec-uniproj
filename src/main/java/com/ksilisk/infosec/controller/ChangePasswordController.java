package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.context.ApplicationContext;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.factory.ApplicationStageFactory;
import com.ksilisk.infosec.factory.DefaultApplicationStageFactory;
import com.ksilisk.infosec.repository.DefaultUserRepository;
import com.ksilisk.infosec.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    @FXML
    private Label userHelloLabel;

    @FXML
    private Button changePasswordButton;
    @FXML
    private Button backButton;

    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;

    private ApplicationContext applicationContext;
    private UserRepository userRepository;
    private ApplicationStageFactory applicationStageFactory;

    public void changePassword(MouseEvent event) {
        try {
            String currentPass = currentPasswordField.getText();
            String newPass = newPasswordField.getText();
            User currentUser = applicationContext.getCurrentUser();
            if (!currentUser.password().equals(currentPass)) {
                new Alert(Alert.AlertType.ERROR, "Current password is incorrect. Try Again!").show();
                return;
            }
            User updatedUser = new User(currentUser.username(), newPass, currentUser.isBlocked(),
                    currentUser.passwordRestriction(), currentUser.isAdmin());

            userRepository.saveUser(updatedUser);
            applicationContext.clear();
            ((Stage) (changePasswordButton.getScene().getWindow())).close();
            applicationStageFactory.createLoginStage().show();
            new Alert(Alert.AlertType.CONFIRMATION, "Password successfully updated!").show();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }


    public void back(MouseEvent event) {
        try {
            if (applicationContext.getCurrentUser().isAdmin()) {
                applicationStageFactory.createAdminStage().show();
            } else {
                applicationStageFactory.createLoginStage().show();
            }
            ((Stage) (changePasswordButton.getScene().getWindow())).close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applicationContext = DefaultApplicationContext.INSTANCE;
        userRepository = DefaultUserRepository.INSTANCE;
        applicationStageFactory = DefaultApplicationStageFactory.INSTANCE;
        userHelloLabel.setText("%s, here you can change your password.".formatted(applicationContext.getCurrentUser().username()));
    }
}
