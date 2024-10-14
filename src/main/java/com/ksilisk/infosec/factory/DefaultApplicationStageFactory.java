package com.ksilisk.infosec.factory;

import com.ksilisk.infosec.config.ApplicationProperties;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public enum DefaultApplicationStageFactory implements ApplicationStageFactory {
    INSTANCE;

    private final ApplicationProperties applicationProperties = ApplicationProperties.INSTANCE;

    @Override
    public Stage createLoginStage() throws IOException {
        FXMLLoader loginStage = new FXMLLoader(applicationProperties.getLoginViewFile().toURI().toURL());
        Stage stage = new Stage();
        stage.setTitle("InfoSec App!");
        stage.setResizable(false);
        stage.setScene(new Scene(loginStage.load()));
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    @Override
    public Stage createAdminStage() throws IOException {
        FXMLLoader adminLoader = new FXMLLoader(applicationProperties.getAdminViewFile().toURI().toURL());
        Parent parent = adminLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Admin Menu");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        return stage;
    }

    @Override
    public Stage createChangePasswordStage() throws IOException {
        FXMLLoader changePassword = new FXMLLoader(applicationProperties.getChangePasswordViewFile().toURI().toURL());
        Parent parent = changePassword.load();
        Stage stage = new Stage();
        stage.setTitle("Change Password");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        return stage;
    }

    @Override
    public Stage createDBPasswordStage() throws IOException {
        FXMLLoader dbPasswordStage = new FXMLLoader(applicationProperties.getDBPasswordViewFile().toURI().toURL());
        Parent parent = dbPasswordStage.load();
        Stage stage = new Stage();
        stage.setTitle("Database password");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        return stage;
    }

    @Override
    public Stage createUserStage() throws IOException {
        FXMLLoader userView = new FXMLLoader(applicationProperties.getUserViewFile().toURI().toURL());
        Parent parent = userView.load();
        Stage stage = new Stage();
        stage.setTitle("User Menu");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        return stage;
    }
}
