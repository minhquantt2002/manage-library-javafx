package btl_java.manage_library.controllers;


import btl_java.manage_library.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

public class LoginController {
    public Stage stage;
    @FXML
    private PasswordField password;
    @FXML
    private Label lblResult;
    @FXML
    private TextField userName;


    public void launchLogin(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/login-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        this.stage.setTitle("Login");
        this.stage.getIcons().add(new Image("images/library_icon.jpg"));
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void btnLogin() throws IOException {


        if(PW.equals(password.getText())){
            new HomeController().displayHome(MainApplication.primaryStage);
        }
        else{
            lblResult.setTextFill(Color.RED);
            lblResult.setText("Wrong username or password");
        }

    }

    String PW = "1234";
    public void btnReset() throws IOException {
        userName.setText("");
        password.setText("");
        lblResult.setTextFill(Color.BLUEVIOLET);
        lblResult.setText("Try again");
    }
}