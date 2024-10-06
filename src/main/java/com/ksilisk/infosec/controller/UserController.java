package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.context.ApplicationContext;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private Label userHelloLabel;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button exitButton;

    private final ApplicationContext applicationContext = DefaultApplicationContext.INSTANCE;

    public void changePassword(MouseEvent event) {

    }

    public void exit(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
