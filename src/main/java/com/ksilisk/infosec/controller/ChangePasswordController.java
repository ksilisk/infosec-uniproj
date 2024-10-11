package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.context.ApplicationContext;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.factory.ApplicationStageFactory;
import com.ksilisk.infosec.factory.DefaultApplicationStageFactory;
import com.ksilisk.infosec.repository.DefaultUserRepository;
import com.ksilisk.infosec.repository.UserRepository;
import com.ksilisk.infosec.validate.DefaultPasswordRestrictionValidator;
import com.ksilisk.infosec.validate.PasswordRestrictionValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;

    private ApplicationContext applicationContext;
    private UserRepository userRepository;
    private ApplicationStageFactory applicationStageFactory;
    private PasswordRestrictionValidator passwordRestrictionValidator;

    public void changePassword() {
        try {
            String currentPass = currentPasswordField.getText();
            String newPass = newPasswordField.getText();
            User currentUser = applicationContext.getCurrentUser();
            if (!currentUser.getPassword().equals(currentPass)) {
                new Alert(Alert.AlertType.ERROR, "Current password is incorrect. Try Again!").show();
                return;
            }
            if (currentUser.getHasPasswordRestriction() && !passwordRestrictionValidator.isValidPassword(newPass)) {
                new Alert(Alert.AlertType.ERROR, "New password not satisfied to restrictions. Try Again!").show();
                return;
            }
            User updatedUser = new User(currentUser.getUsername(), newPass, currentUser.getIsBlocked(),
                    currentUser.getHasPasswordRestriction(), currentUser.getIsAdmin());

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


    public void back() {
        try {
            if (applicationContext.getCurrentUser().getIsAdmin()) {
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
        passwordRestrictionValidator = DefaultPasswordRestrictionValidator.INSTANCE;
        userHelloLabel.setText("%s, here you can change your password.".formatted(applicationContext.getCurrentUser().getUsername()));
    }
}
