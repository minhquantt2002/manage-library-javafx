package btl_java.manage_library.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertWarningUtils {
    private final String codeStudentField;
    private final String nameStudentField;
    private final String classStudentField;
    private final String phoneNumberStudentField;

    public AlertWarningUtils(String codeStudentField, String nameStudentField, String classStudentField, String phoneNumberStudentField) {
        this.codeStudentField = codeStudentField;
        this.nameStudentField = nameStudentField;
        this.classStudentField = classStudentField;
        this.phoneNumberStudentField = phoneNumberStudentField;
    }

    public boolean checkValid() {
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
    public void showAlertWarning(String logError) {
        Alert dialogAlert = new Alert(Alert.AlertType.WARNING);
        dialogAlert.setTitle("Warning");
        dialogAlert.setHeaderText(logError.toString());
        Stage stage = (Stage) dialogAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("images/library_icon.jpg"));
        dialogAlert.showAndWait();
    }
}
