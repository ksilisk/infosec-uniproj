module com.ksilisk.infosec {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens com.ksilisk.infosec to javafx.fxml;
    exports com.ksilisk.infosec;
    exports com.ksilisk.infosec.controller;
    exports com.ksilisk.infosec.entity;
    opens com.ksilisk.infosec.controller to javafx.fxml;
}