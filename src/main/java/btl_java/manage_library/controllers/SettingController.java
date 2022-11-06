package btl_java.manage_library.controllers;

import btl_java.manage_library.MainApplication;
import btl_java.manage_library.utils.ConnectionUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.w3c.dom.Node;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SettingController {
    public TextField userfield;
    public TextField confirmPasswordfield;
    public TextField passwordfield;
    public TextField newPasswordField;
    @FXML
    private StackPane contentArea;
    public void btnLogout() throws IOException {
        new LoginController().launchLogin(MainApplication.primaryStage);
    }

    public void huongdan() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/guide.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void changePassword() throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/changePassword.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
//    ===============================CHANGE PASSWORD==================================

    public void updatePassword(ActionEvent actionEvent) {
        String Username = userfield.getText();
        String Password = passwordfield.getText();
        String newPassword =newPasswordField.getText();
        String confirmPassword =confirmPasswordfield.getText();
        System.out.println(Password);
        if (Username.equals("") || Password.equals("")|| newPassword.equals("")|| confirmPassword.equals("")) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Chưa điền đủ thông tin", "", "Điền thông tin", ButtonType.CLOSE);
            warn.show();
        }
        else {
            try {
                Connection connection = new ConnectionUtils().connectDB();
                String stmt = "SELECT * FROM accounts WHERE username=? AND password=?";
                PreparedStatement preparedStatement = connection.prepareStatement(stmt);
                preparedStatement.setString(1, Username);
                preparedStatement.setString(2, Password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next() ) {
                    if(newPassword.equals(confirmPassword))
                    {
                        String stmt1="UPDATE accounts SET  password='" + newPassword + "' WHERE username='" + Username + "' AND password='" + Password + "'";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(stmt1);
                        preparedStatement1.executeUpdate();
//                    System.out.println(newPassword);
                        Alert warn = this.createAlert(Alert.AlertType.INFORMATION, "Thay đổi mật khẩu thành công  ! ", "", "Đổi mật khẩu ");
                        warn.show();
                    }else {
                        Alert warn = this.createAlert(Alert.AlertType.WARNING, "Sai mật khẩu , nhập lại !  ", "", "Error", ButtonType.CLOSE);
                        warn.show();
                        clear1();
                    }
                } else {
                    Alert warn = this.createAlert(Alert.AlertType.WARNING, "Tài khoản không tồn tại , nhập lại ! ", "", "Error", ButtonType.CLOSE);
                    warn.show();
                    clear();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

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
        userfield.setText("");
        passwordfield.setText("");
        newPasswordField.setText("");
        confirmPasswordfield.setText("");
    }
    public void clear1() {
        newPasswordField.setText("");
        confirmPasswordfield.setText("");
    }
}
