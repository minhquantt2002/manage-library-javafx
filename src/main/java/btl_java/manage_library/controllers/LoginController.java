package btl_java.manage_library.controllers;


import btl_java.manage_library.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Stage stage;


    public void launchLogin(Stage primaryStage) throws IOException {
        this.stage = MainApplication.primaryStage;
        System.out.println(this.stage);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/login-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        this.stage.setTitle("Login");
        this.stage.getIcons().add(new Image("images/library_icon.jpg"));
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void btn_submit(ActionEvent actionEvent) throws IOException {

        displayHome(MainApplication.primaryStage);
    }
    public void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setScene(scene);
        stage.show();
    }
}