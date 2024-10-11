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
        FXMLLoader fxmlLoader = new FXMLLoader(applicationProperties.getLoginViewFile().toURI().toURL());
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("InfoSec App!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    @Override
    public Stage createAdminStage() throws IOException {
        FXMLLoader adminLoader = new FXMLLoader(applicationProperties.getAdminViewFile().toURI().toURL());
        Parent parent = adminLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        return stage;
    }

    @Override
    public Stage createChangePasswordStage() throws IOException {
        FXMLLoader adminLoader = new FXMLLoader(applicationProperties.getChangePasswordViewFile().toURI().toURL());
        Parent parent = adminLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        return stage;
    }
}
