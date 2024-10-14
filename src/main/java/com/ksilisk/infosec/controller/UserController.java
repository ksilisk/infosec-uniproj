package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.context.ApplicationContext;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import com.ksilisk.infosec.customizer.MenuBarCustomizer;
import com.ksilisk.infosec.factory.ApplicationStageFactory;
import com.ksilisk.infosec.factory.DefaultApplicationStageFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private MenuBar menuBar;

    private ApplicationStageFactory applicationStageFactory;
    private ApplicationContext applicationContext;

    public void changePassword() {
        try {
            applicationStageFactory.createChangePasswordStage().show();
            ((Stage) (menuBar.getScene().getWindow())).close();
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }

    public void exit() {
        try {
            applicationContext.clear();
            applicationStageFactory.createLoginStage().show();
            ((Stage) (menuBar.getScene().getWindow())).close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applicationStageFactory = DefaultApplicationStageFactory.INSTANCE;
        applicationContext = DefaultApplicationContext.INSTANCE;
        MenuBarCustomizer.INSTANCE.customize(menuBar);
    }
}
