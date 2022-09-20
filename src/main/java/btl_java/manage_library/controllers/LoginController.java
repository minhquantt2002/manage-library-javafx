package btl_java.manage_library.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent parent;
    @FXML
    private Button submitLogin;

    // TODO Chưa gọi đuợc đến file login-app.fxml
    public LoginController() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("fxml/login-app.fxml"));
         this.scene = new Scene(fxmlLoader.load(), 500, 500);
    }

    public void launchLogin(Stage stage) {
        this.stage = stage;
        stage.setTitle("Login");
        stage.getIcons().add(new Image("images/library_icon.jpg"));
        stage.setScene(this.scene);
        stage.setResizable(true);
        stage.show();
    }

    public void authenticateUser() {

        // TODO Xử lý login, sau khi login xong -> redirect sang controller HomeController

    }
}