package btl_java.manage_library.controllers;


import btl_java.manage_library.MainApplication;
import btl_java.manage_library.utils.AlertWarningUtils;
import btl_java.manage_library.utils.ConnectionUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;


    public void launchLogin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/login-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Login");
        stage.getIcons().add(new Image("images/library_icon.jpg"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        ;
        if (username.equals("") || password.equals("")) {
            new AlertWarningUtils().showAlertWarning("Chưa điền đủ thông tin!");
            return;
        }
        try {
            Connection connection = new ConnectionUtils().connectDB();
            String stmt = "SELECT * FROM accounts WHERE username=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                new HomeController().displayHome(MainApplication.primaryStage);
            } else {
                new AlertWarningUtils().showAlertWarning("Tài khoản hoặc mật khẩu nhập sai.\nVui lòng nhập lại!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}