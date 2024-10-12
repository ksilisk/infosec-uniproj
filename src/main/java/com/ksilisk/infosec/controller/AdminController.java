package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.context.ApplicationContext;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import com.ksilisk.infosec.customizer.MenuBarCustomizer;
import com.ksilisk.infosec.entity.User;
import com.ksilisk.infosec.factory.ApplicationStageFactory;
import com.ksilisk.infosec.factory.DefaultApplicationStageFactory;
import com.ksilisk.infosec.repository.DefaultUserRepository;
import com.ksilisk.infosec.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class AdminController implements Initializable {
    @FXML
    private MenuBar menuBar;

    @FXML
    private TableView<User> userView;

    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, Boolean> isBlockedColumn;
    @FXML
    private TableColumn<User, Boolean> hasPasswordRestrictionColumn;
    @FXML
    private TableColumn<User, Boolean> isAdminColumn;

    @FXML
    private Label adminHelloLabel;

    private ApplicationContext applicationContext;
    private ApplicationStageFactory applicationStageFactory;
    private UserRepository userRepository;

    public void addUser() {
        userView.getItems().add(new User());
    }

    public void save() {
        try {
            List<String> distinctUsernames = userView.getItems()
                    .stream()
                    .map(User::getUsername)
                    .distinct()
                    .toList();
            if (distinctUsernames.size() != userView.getItems().size()) {
                new Alert(Alert.AlertType.ERROR, "All usernames should be unique. Try again!").show();
                return;
            }

            if (distinctUsernames.contains(null) || distinctUsernames.contains("")) {
                new Alert(Alert.AlertType.ERROR, "User username can't be empty. Try Again!").show();
                return;
            }
            userRepository.saveAllUsers(new ArrayList<>(userView.getItems()));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }

    public void changePassword() {
        try {
            applicationStageFactory.createChangePasswordStage().show();
            ((Stage) (adminHelloLabel.getScene().getWindow())).close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error.").show();
        }
    }

    public void exit() {
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
        applicationStageFactory = DefaultApplicationStageFactory.INSTANCE;
        userRepository = DefaultUserRepository.INSTANCE;
        adminHelloLabel.setText("Hello, %s!".formatted(applicationContext.getCurrentUser().getUsername()));
        MenuBarCustomizer.INSTANCE.customize(menuBar);
        initUserTableView();
    }

    private void initUserTableView() {
        // init table columns cell factories
        isAdminColumn.setCellFactory(forTableColumn(new BooleanStringConverter()));
        hasPasswordRestrictionColumn.setCellFactory(forTableColumn(new BooleanStringConverter()));
        isBlockedColumn.setCellFactory(forTableColumn(new BooleanStringConverter()));
        usernameColumn.setCellFactory(forTableColumn(new DefaultStringConverter()));
        passwordColumn.setCellFactory(forTableColumn(new DefaultStringConverter()));

        // init edit commit actions
        isBlockedColumn.setOnEditCommit(e ->
                e.getTableView()
                        .getItems()
                        .get(e.getTablePosition().getRow())
                        .setIsBlocked(e.getNewValue()));
        isAdminColumn.setOnEditCommit(e ->
                e.getTableView()
                        .getItems()
                        .get(e.getTablePosition().getRow())
                        .setIsAdmin(e.getNewValue()));
        hasPasswordRestrictionColumn.setOnEditCommit(e ->
                e.getTableView()
                        .getItems()
                        .get(e.getTablePosition().getRow())
                        .setHasPasswordRestriction(e.getNewValue()));
        usernameColumn.setOnEditCommit(e ->
                e.getTableView()
                        .getItems()
                        .get(e.getTablePosition().getRow())
                        .setUsername(e.getNewValue()));
        passwordColumn.setOnEditCommit(e ->
                e.getTableView()
                        .getItems()
                        .get(e.getTablePosition().getRow())
                        .setPassword(e.getNewValue()));

        // init table cell value factories
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        isBlockedColumn.setCellValueFactory(new PropertyValueFactory<>("isBlocked"));
        isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        hasPasswordRestrictionColumn.setCellValueFactory(new PropertyValueFactory<>("hasPasswordRestriction"));

        // upload all users
        userView.getItems().addAll(userRepository.findAllUsers());
    }
}
