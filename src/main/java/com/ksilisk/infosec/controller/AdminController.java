package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

public class AdminController {
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

    public void addUser(MouseEvent event) {

    }

    public void changePassword(MouseEvent event) {

    }

    public void exit(MouseEvent event) {

    }
}
