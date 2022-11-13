package btl_java.manage_library.controllers;

import btl_java.manage_library.MainApplication;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HomeController {
    public void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
        stage.setTitle("Quản lí thư viện");
        stage.show();
    }
}