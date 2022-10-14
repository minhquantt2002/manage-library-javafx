package btl_java.manage_library.controllers;

import btl_java.manage_library.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class HomeController {

    public Button btnChangePassword;

    public void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),702,596);
        stage.setScene(scene);
        stage.setTitle("Quản lí thư viện");
        stage.show();
    }

    public void ChangePassword(ActionEvent event) {

    }

    public void btnDialog() {
        TextInputDialog log = new TextInputDialog();
        log.setTitle("Nhập mã sinh viên");
        log.setHeaderText("Nhập mã sinh viên");
        log.setContentText("MSV");
        Optional<String> result = log.showAndWait();
        System.out.println(result);

    }
}