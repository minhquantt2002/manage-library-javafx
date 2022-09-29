package btl_java.manage_library.controllers;

import btl_java.manage_library.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    public void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
//        stage.setMaximized(true);
        stage.show();
    }
}