package btl_java.manage_library.controllers;


import btl_java.manage_library.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button submitLogin;
    public void launchLogin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/login-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Login");
        stage.getIcons().add(new Image("images/library_icon.jpg"));
        stage.setScene(scene);
        stage.show();
        // TODO: đã fix đc :))
    }

    public void authenticateUser() {

        // TODO Xử lý login, sau khi login xong -> redirect sang controller HomeController

    }
}