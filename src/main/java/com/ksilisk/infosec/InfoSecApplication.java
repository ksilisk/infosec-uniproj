package com.ksilisk.infosec;

import com.ksilisk.infosec.close.DefaultApplicationCloser;
import com.ksilisk.infosec.factory.ApplicationStageFactory;
import com.ksilisk.infosec.factory.DefaultApplicationStageFactory;
import com.ksilisk.infosec.initialization.DefaultApplicationInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoSecApplication extends Application {
    private final ApplicationStageFactory applicationStageFactory = DefaultApplicationStageFactory.INSTANCE;

    @Override
    public void init() throws Exception {
        DefaultApplicationInitializer.INSTANCE.initialize();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Stage dbPasswordStage = applicationStageFactory.createDBPasswordStage();
        dbPasswordStage.initOwner(stage);
        dbPasswordStage.show();
    }

    @Override
    public void stop() throws Exception {
        DefaultApplicationCloser.INSTANCE.close();
    }

    public static void main(String[] args) {
        launch();
    }
}