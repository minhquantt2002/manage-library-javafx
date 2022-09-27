package btl_java.manage_library.controllers;


import btl_java.manage_library.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

public class LoginController {
    public Stage stage;
    @FXML
    private TextField password;
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
        TextInputDialog log = new TextInputDialog();
        log.setTitle("Nhập mã sinh viên");
        log.setHeaderText("Nhập mã sinh viên");
        log.setContentText("MSV");
        Optional<String> result = log.showAndWait();
        System.out.println(result);
        if (result.isPresent() && result.get().equals("123")) {

            new HomeController().displayHome(MainApplication.primaryStage);
        }
    }

    public void btnReset() throws IOException {
        userName.setText("");
        password.setText("");
    }
}