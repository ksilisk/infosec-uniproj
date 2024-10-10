package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.context.ApplicationContext;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.factory.ApplicationStageFactory;
import com.ksilisk.infosec.factory.DefaultApplicationStageFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, Boolean> isBlockedColumn;
    @FXML
    private TableColumn<User, String> passwordRestrictionColumn;
    @FXML
    private TableColumn<User, Boolean> isAdminColumn;

    @FXML
    private Button addUserButton;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button exitButton;

    @FXML
    private Label adminHelloLabel;

    private ApplicationContext applicationContext;
    private ApplicationProperties applicationProperties;
    private ApplicationStageFactory applicationStageFactory;

    public void addUser(MouseEvent event) {
        // todo implement this
    }

    public void changePassword(MouseEvent event) {
        try {
            applicationStageFactory.createChangePasswordStage().show();
            ((Stage) (adminHelloLabel.getScene().getWindow())).close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }

    public void exit(MouseEvent event) {
        try {
            applicationContext.clear();
            applicationStageFactory.createLoginStage().show();
            ((Stage) (adminHelloLabel.getScene().getWindow())).close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applicationContext = DefaultApplicationContext.INSTANCE;
        applicationProperties = ApplicationProperties.INSTANCE;
        applicationStageFactory = DefaultApplicationStageFactory.INSTANCE;
        adminHelloLabel.setText("Hello, %s!".formatted(applicationContext.getCurrentUser().username()));
    }
}
