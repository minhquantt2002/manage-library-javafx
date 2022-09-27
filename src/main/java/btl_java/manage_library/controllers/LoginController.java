package btl_java.manage_library.controllers;


import btl_java.manage_library.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    public Stage stage;
    @FXML
    TextField PassWord;
    @FXML
    Label lblResult;

    public void launchLogin(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/login-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        this.stage.setTitle("Login");
        this.stage.getIcons().add(new Image("images/library_icon.jpg"));
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void btn_submit() throws IOException {
//        if (PassWord.getText().equals("1234")) {
//            new HomeController().displayHome(MainApplication.primaryStage);
//        } else {
//            lblResult.setTextFill(Color.RED);
//            lblResult.setText("mật khẩu sai vui lòng nhập lại");
//        }
        TextInputDialog log = new TextInputDialog();
        log.setTitle("Nhập mã sinh viên");
        log.setHeaderText("Nhập mã sinh viên");
        log.setContentText("Mã sinh viên");
        Optional<String> result = log.showAndWait();
        System.out.println(result);
    }
}