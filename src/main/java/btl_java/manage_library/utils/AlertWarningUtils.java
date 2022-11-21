package btl_java.manage_library.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertWarningUtils {
    public boolean checkValidStudent(String codeStudentField, String nameStudentField, String classStudentField, String phoneNumberStudentField) {
        int i = 1;
        StringBuilder logError = new StringBuilder();
        if (codeStudentField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường mã sinh viên! \n");
            i++;
        }
        if (nameStudentField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường tên sinh viên! \n");
            i++;
        }
        if (classStudentField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường lớp sinh viên! \n");
            i++;
        }
        if (phoneNumberStudentField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường số điện thoại sinh viên! \n");
        }
        if (logError.toString().equals("")) {
            return true;
        }
        showAlertWarning(logError.toString());
        return false;
    }

    public boolean checkValidBook(String codeBookField, String categoryBookField, String nameBookField, String authorBookField, String totalBookField) {
        int i = 1;
        StringBuilder logError = new StringBuilder();
        if (codeBookField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường mã sách! \n");
            i++;
        }
        if (categoryBookField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường thể loại sách! \n");
            i++;
        }
        if (nameBookField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường tên sách! \n");
            i++;
        }
        if (authorBookField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường tên tác giả sách! \n");
            i++;
        }
        if (totalBookField.equals("")) {
            logError.append(i).append(". Thiếu dữ liệu trường tổng lượng sách nhập! \n");
        } else {
            try {
                int tt = Integer.parseInt(totalBookField);
            } catch (Exception e) {
                logError.append(i).append(". Tổng lượng sách là kiểu số nguyên!");
            }
        }
        if (logError.toString().equals("")) {
            return true;
        }
        showAlertWarning(logError.toString());
        return false;
    }

    public void showAlertWarning(String logError) {
        Alert dialogAlert = new Alert(Alert.AlertType.WARNING);
        dialogAlert.setTitle("Warning");
        dialogAlert.setHeaderText(logError);
        Stage stage = (Stage) dialogAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("images/library_icon.jpg"));
        dialogAlert.showAndWait();
    }
}
