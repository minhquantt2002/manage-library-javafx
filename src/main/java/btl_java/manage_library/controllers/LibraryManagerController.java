package btl_java.manage_library.controllers;

import btl_java.manage_library.utils.ConnectionUtils;
import btl_java.manage_library.models.LibraryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import btl_java.manage_library.utils.ValidatorInputFieldUtils;

public class  LibraryManagerController implements Initializable {
    @FXML
    private TextField idStudentField;
    @FXML
    private TextField nameStudentField;
    @FXML
    private TextField classStudentField;
    @FXML
    private TextField phoneNumberStudentField;
    @FXML
    private TableView<LibraryModel> tableViewLbm;
    @FXML
    private TableColumn<LibraryModel, String> timeIn;
    @FXML
    private TableColumn<LibraryModel, String> timeOut;
    @FXML
    private TableColumn<LibraryModel, String> codeStudent;
    @FXML
    private TableColumn<LibraryModel, String> nameStudent;
    @FXML
    private TableColumn<LibraryModel, String> classStudent;
    @FXML
    private TableColumn<LibraryModel, String> phoneNumber;
    @FXML
    private TableColumn<LibraryModel, String> stt;
    private String stmtQueryAll = "SELECT * FROM library_manager";

    public void initialize(URL url, ResourceBundle rb) {
        stt.setCellValueFactory(cellData -> cellData.getValue().getStt());
        codeStudent.setCellValueFactory(cellData -> cellData.getValue().getCodeStudent());
        nameStudent.setCellValueFactory(cellData -> cellData.getValue().getNameStudent());
        classStudent.setCellValueFactory(cellData -> cellData.getValue().getClassStudent());
        phoneNumber.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        timeIn.setCellValueFactory(cellData -> cellData.getValue().getTimeIn());
        timeOut.setCellValueFactory(cellData -> cellData.getValue().getTimeOut());
        setDataTableView(stmtQueryAll);
    }

    public void setDataTableView(String stmt) {
        Connection connection = new ConnectionUtils().connectDB();
        ObservableList<LibraryModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery(stmt);
            while (resultSet.next()) {
                LibraryModel row = new LibraryModel(
                        Integer.toString(i),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                i++;
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableViewLbm.setItems(list);
    }

    //==========================================INSERTSTUDENT=============================================
    @FXML
    private void insertStudent() {
        boolean valid = checkInput();
        if (!valid) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Chưa điền đủ thông tin", "", "Điền thông tin", ButtonType.CLOSE);
            warn.show();
        } else {
            String stmt = "INSERT INTO library_manager (student_code, full_name, class_name, phone_number, time_in, time_out) VALUES (?, ?, ?, ?, ?, ?)";
            Connection connection = new ConnectionUtils().connectDB();
            try {
                String timeInToString = DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy").format(LocalDateTime.now());
                PreparedStatement preparedStatement = connection.prepareStatement(stmt);
                preparedStatement.setString(1, new ValidatorInputFieldUtils().ValidateAllToUpperCase(idStudentField.getText()));
                preparedStatement.setString(2, new ValidatorInputFieldUtils().ValidateFirstUpperCase(nameStudentField.getText()));
                preparedStatement.setString(3, new ValidatorInputFieldUtils().ValidateAllToUpperCase(classStudentField.getText()));
                preparedStatement.setString(4, phoneNumberStudentField.getText());
                preparedStatement.setString(5, timeInToString);
                preparedStatement.setString(6, "");
                preparedStatement.executeUpdate();
                System.out.println("Create done a record: " + new LibraryModel(idStudentField.getText(), nameStudentField.getText(),
                        classStudentField.getText(), phoneNumberStudentField.getText(), timeInToString, timeInToString, ""));
                this.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setDataTableView(stmtQueryAll);
        }

    }

    //==========================================DELETESTUDENT======================================
    @FXML
    private void deleteStudent() {
        LibraryModel selected = tableViewLbm.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Bạn có chắc muốn xóa thông tin về sinh viên này ? ", "", "Xoá thông tin sinh viên ", ButtonType.CLOSE);
            Optional<ButtonType> option = warn.showAndWait();
            try {
                if (ButtonType.OK == option.get()) {
                    String query = "DELETE FROM library_manager WHERE time_in ='" + selected.getTimeIn().getValue() + "'";
                    try {
                        Connection conn = new ConnectionUtils().connectDB();
                        PreparedStatement pstm = conn.prepareStatement(query);
                        pstm.executeUpdate();
                        System.out.println("Delete done a record: " + selected);
                    } catch (SQLException err) {
                        System.err.println("Delete : " + err.getMessage());
                    }
                    setDataTableView(stmtQueryAll);
                }
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
            }
        }
    }

    public boolean checkInput() {
        return !idStudentField.getText().isEmpty() && !nameStudentField.getText().isEmpty() && !classStudentField.getText().isEmpty() && !phoneNumberStudentField.getText().isEmpty();
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
        idStudentField.setText("");
        nameStudentField.setText("");
        classStudentField.setText("");
        phoneNumberStudentField.setText("");
    }

    //========================================SEARCHSTUDENT===========================================
    @FXML
    private void searchStudent() {
        String find = "";
        String temp = "";
        if (idStudentField.getText().equals("") && nameStudentField.getText().equals("") && !classStudentField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().ValidateAllToUpperCase(classStudentField.getText());
            temp = "SELECT * FROM library_manager WHERE class_name LIKE '%" + find + "%'";
        } else if (idStudentField.getText().equals("") && !nameStudentField.getText().equals("") && classStudentField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().ValidateFirstUpperCase(nameStudentField.getText());
            temp = "SELECT * FROM library_manager WHERE full_name LIKE '%" + find + "%'";
        } else if (!idStudentField.getText().equals("") && nameStudentField.getText().equals("") && classStudentField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().ValidateAllToUpperCase(idStudentField.getText());
            temp = "SELECT * FROM library_manager WHERE student_code LIKE '%" + find + "%'";
        }
        if (find.equals("")) {
            setDataTableView(stmtQueryAll);
        } else {
            setDataTableView(temp);
        }
        clear();
    }

    //===============================================UPDATESTUDENT=========================================================
    public void updateStudent() {
        String findID = new ValidatorInputFieldUtils().ValidateAllToUpperCase(idStudentField.getText());
        if (nameStudentField.getText().equals("") && classStudentField.getText().equals("") && phoneNumberStudentField.getText().equals("") && !findID.equals("")) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Thêm thông tin cho sinh viên này ? ", "", "Thêm thông tin sinh viên ", ButtonType.CLOSE);
            Optional<ButtonType> option = warn.showAndWait();
            try {
                if (ButtonType.OK == option.get()) {
                    String timeOutToString = DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy").format(LocalDateTime.now());
                    String stmt = "UPDATE library_manager SET time_out='" + timeOutToString + "' WHERE student_code='" + findID + "'AND time_out =''";
                    Connection conn = new ConnectionUtils().connectDB();
                    try {
                        PreparedStatement preparedStatement = conn.prepareStatement(stmt);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    setDataTableView(stmtQueryAll);
                }
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
            }
        }
        clear();
    }
}
