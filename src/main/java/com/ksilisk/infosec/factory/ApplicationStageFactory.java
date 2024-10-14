package com.ksilisk.infosec.factory;

import javafx.stage.Stage;

import java.io.IOException;

public interface ApplicationStageFactory {
    Stage createLoginStage() throws IOException;

    Stage createAdminStage() throws IOException;

    Stage createChangePasswordStage() throws IOException;

    Stage createDBPasswordStage() throws IOException;

    Stage createUserStage() throws IOException;
}
