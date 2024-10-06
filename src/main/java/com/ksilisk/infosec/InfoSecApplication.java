package com.ksilisk.infosec;

import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.initialize.DefaultApplicationInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoSecApplication extends Application {
    private final ApplicationProperties applicationProperties = ApplicationProperties.INSTANCE;

    @Override
    public void init() throws Exception {
        DefaultApplicationInitializer.INSTANCE.initialize();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(applicationProperties.getLoginViewFile().toURI().toURL());
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("InfoSec App!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {

    }

    public static void main(String[] args) {
        launch();
    }
}