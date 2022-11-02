package btl_java.manage_library.controllers;

import btl_java.manage_library.MainApplication;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SettingController {

    public void btnLogout() throws IOException {
        new LoginController().launchLogin(MainApplication.primaryStage);
    }
}
