package com.ksilisk.infosec.controller;

import com.ksilisk.infosec.customizer.MenuBarCustomizer;
import com.ksilisk.infosec.factory.ApplicationStageFactory;
import com.ksilisk.infosec.factory.DefaultApplicationStageFactory;
import com.ksilisk.infosec.repository.DefaultUserRepository;
import com.ksilisk.infosec.repository.UserRepository;
import com.ksilisk.infosec.security.CipherCoder;
import com.ksilisk.infosec.security.DefaultCipherCoder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.ResourceBundle;

public class GetDBPasswordController implements Initializable {
    @FXML
    private MenuBar menuBar;

    @FXML
    private PasswordField dbPasswordField;

    private UserRepository userRepository;
    private CipherCoder cipherCoder;
    private ApplicationStageFactory stageFactory;

    public void load() {
        try {
            String dbPassword = dbPasswordField.getText();
            cipherCoder.setSecretKey(dbPassword);
            userRepository.load();
            stageFactory.createLoginStage().show();
            ((Stage) (dbPasswordField.getScene().getWindow())).close();
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Invalid database password. Try Again!").show();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            new Alert(Alert.AlertType.ERROR, "Internal application error.").show();
        }
    }

    public void exit() {
        Platform.exit();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userRepository = DefaultUserRepository.INSTANCE;
        cipherCoder = DefaultCipherCoder.INSTANCE;
        stageFactory = DefaultApplicationStageFactory.INSTANCE;
        MenuBarCustomizer.INSTANCE.customize(menuBar);
    }
}
