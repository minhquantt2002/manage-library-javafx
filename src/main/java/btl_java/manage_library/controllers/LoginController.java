package btl_java.manage_library.controllers;


import btl_java.manage_library.MainApplication;
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
    private PasswordField passWord;
    @FXML
    private Label lblResult;
    @FXML
    private TextField userName;



    public void launchLogin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/login-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Login");
        stage.getIcons().add(new Image("images/library_icon.jpg"));
        stage.setScene(scene);
        stage.show();
    }
//===========================LOGIN============================
    public void btnLogin() throws IOException {
        new HomeController().displayHome(MainApplication.primaryStage);

//        String Username = userName.getText();
//        String Password = passWord.getText();
//        System.out.println(Password);
//        if (Username.equals("") || Password.equals("")) {
//            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Chưa điền đủ thông tin", "", "Điền thông tin", ButtonType.CLOSE);
//            warn.show();
//            clear();
//            return;
//        }
//        try {
//            Connection connection = new ConnectionUtils().connectDB();
//            String stmt = "SELECT * FROM accounts WHERE username=? AND password=?";
//            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
//            preparedStatement.setString(1, Username);
//            preparedStatement.setString(2, Password);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                new HomeController().displayHome(MainApplication.primaryStage);
//            } else {
//                lblResult.setTextFill(Color.RED);
//                lblResult.setText("Wrong username or password");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


    }

    public Alert createAlert(Alert.AlertType type, String content, String header, String title, ButtonType... buttonTypes) {
        Alert alert = this.createAlert(type, content, header, title);
        alert.getButtonTypes().addAll(buttonTypes);
        return alert;
    }

    private Alert createAlert(Alert.AlertType type, String content, String header, String title) {
        Alert alert = new Alert(type, content);
        alert.setHeaderText(header);
        alert.setTitle(title);
        return alert;
    }

    public void clear() {
        userName.setText("");
        passWord.setText("");
    }

    public void btnReset() throws IOException {
        userName.setText("");
        passWord.setText("");
        lblResult.setTextFill(Color.BLUEVIOLET);
        lblResult.setText("Try again");
    }
    //===========================REGISTER============================
    public void btnRegister() {
        boolean valid = checkInput();
        if (!valid) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Chưa điền đủ thông tin", "", "Điền thông tin", ButtonType.CLOSE);
            warn.show();
            clear();
            return;
        }
        try {
            String stmt = "INSERT INTO accounts (username,password) VALUES (?, ?)";
            Connection connection = new ConnectionUtils().connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1,userName.getText());
            preparedStatement.setString(2,passWord.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkInput() {
        return !userName.getText().isEmpty() && !passWord.getText().isEmpty();
    }
}