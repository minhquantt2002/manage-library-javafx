package btl_java.manage_library.controllers;

import btl_java.manage_library.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import btl_java.manage_library.controllers.LoginController;
import java.io.IOException;


public class HomeController {

    public void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/test.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),702,596);
        stage.setScene(scene);
        stage.setTitle("Quản lí thư viện");
        stage.show();
    }

    public void ChangePassword(ActionEvent event) {

    }
}