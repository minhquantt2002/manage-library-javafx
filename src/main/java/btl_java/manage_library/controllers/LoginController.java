package btl_java.manage_library.controllers;


import btl_java.manage_library.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class LoginController {
    public Stage stage;
    @FXML
    TextField PassWord;
    public void launchLogin(Stage primaryStage) throws IOException {
        System.out.println(primaryStage);
        this.stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/login-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        this.stage.setTitle("Login");
        this.stage.getIcons().add(new Image("images/library_icon.jpg"));
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void btn_submit() throws IOException {
        if(PassWord.getText().equals("1234")){
            new HomeController().displayHome(MainApplication.primaryStage);
        }
    }
}