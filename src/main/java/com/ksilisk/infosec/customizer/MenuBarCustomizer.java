package com.ksilisk.infosec.customizer;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public enum MenuBarCustomizer implements Customizer<MenuBar> {
    INSTANCE;

    private static final String ABOUT_MENU_ITEM_TEXT = "Application created by Shaliko Salimov (ksilisk) at 2024.";

    @Override
    public void customize(MenuBar object) {
        object.setUseSystemMenuBar(true);
        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> new Alert(Alert.AlertType.INFORMATION, ABOUT_MENU_ITEM_TEXT).show());
        helpMenu.getItems().add(aboutMenuItem);
        object.getMenus().add(helpMenu);
    }
}
