module com.ksilisk.infosec {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.ksilisk.infosec to javafx.fxml;
    exports com.ksilisk.infosec;
}