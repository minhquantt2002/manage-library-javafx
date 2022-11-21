package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.utils.AlertWarningUtils;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

public class LibraryManagerController implements Initializable {
    @FXML
    private TextField codeStudentField;
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
    Connection connection = new ConnectionUtils().connectDB();

    private final String stmtQueryAll = "SELECT * FROM library_manager ORDER BY time_out";

    private final Callback<TableView<LibraryModel>, TableRow<LibraryModel>> doubleClickTable = bookModelTableView -> {
        TableRow<LibraryModel> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                LibraryModel clickStudent = row.getItem();
                codeStudentField.setText(clickStudent.getCodeStudent().getValue());
                nameStudentField.setText(clickStudent.getNameStudent().getValue());
                classStudentField.setText(clickStudent.getClassStudent().getValue());
                phoneNumberStudentField.setText(clickStudent.getPhoneNumber().getValue());
            }
        });
        return row;
    };

    public void initialize(URL url, ResourceBundle rb) {
        stt.setCellValueFactory(cellData -> cellData.getValue().getStt());
        codeStudent.setCellValueFactory(cellData -> cellData.getValue().getCodeStudent());
        nameStudent.setCellValueFactory(cellData -> cellData.getValue().getNameStudent());
        classStudent.setCellValueFactory(cellData -> cellData.getValue().getClassStudent());
        phoneNumber.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        timeIn.setCellValueFactory(cellData -> cellData.getValue().getTimeIn());
        timeOut.setCellValueFactory(cellData -> cellData.getValue().getTimeOut());

        setDataTableView(stmtQueryAll);
        tableViewLbm.setRowFactory(doubleClickTable);
        tableViewLbm.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

    @FXML
    private void insertStudent() {
        boolean isValid = new AlertWarningUtils().checkValidStudent(codeStudentField.getText(), nameStudentField.getText(), classStudentField.getText(), phoneNumberStudentField.getText());
        if (!isValid) {
            return;
        }
        try {
            ResultSet isPresent = connection.createStatement().executeQuery("SELECT id FROM library_manager WHERE student_code = '" +  codeStudentField.getText()
                    + "' and time_out = ''");
            if (isPresent.next()) {
                new AlertWarningUtils().showAlertWarning("Sinh viên này chưa ra khỏi thư viện!");
                return;
            }
            String stmt = "INSERT INTO library_manager (student_code, full_name, class_name, phone_number, time_in, time_out) VALUES (?, ?, ?, ?, ?, ?)";
            String timeInToString = DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy").format(LocalDateTime.now());
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1, new ValidatorInputFieldUtils().ValidateAllToUpperCase(codeStudentField.getText()));
            preparedStatement.setString(2, new ValidatorInputFieldUtils().ValidateFirstUpperCase(nameStudentField.getText()));
            preparedStatement.setString(3, new ValidatorInputFieldUtils().ValidateAllToUpperCase(classStudentField.getText()));
            preparedStatement.setString(4, phoneNumberStudentField.getText());
            preparedStatement.setString(5, timeInToString);
            preparedStatement.setString(6, "");
            preparedStatement.executeUpdate();
            System.out.println("Create done a record: " + new LibraryModel(codeStudentField.getText(), nameStudentField.getText(),
                    classStudentField.getText(), phoneNumberStudentField.getText(), timeInToString, timeInToString, ""));
            this.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setDataTableView(stmtQueryAll);
    }

    @FXML
    private void searchStudent() {
        String find = "";
        String temp = "";
        if (codeStudentField.getText().equals("") && nameStudentField.getText().equals("") && !classStudentField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().ValidateAllToUpperCase(classStudentField.getText());
            temp = "SELECT * FROM library_manager WHERE class_name LIKE '%" + find + "%'";
        } else if (codeStudentField.getText().equals("") && !nameStudentField.getText().equals("") && classStudentField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().ValidateFirstUpperCase(nameStudentField.getText());
            temp = "SELECT * FROM library_manager WHERE full_name LIKE '%" + find + "%'";
        } else if (!codeStudentField.getText().equals("") && nameStudentField.getText().equals("") && classStudentField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().ValidateAllToUpperCase(codeStudentField.getText());
            temp = "SELECT * FROM library_manager WHERE student_code LIKE '%" + find + "%'";
        }
        if (find.equals("")) {
            setDataTableView(stmtQueryAll);
        } else {
            setDataTableView(temp);
        }
        clear();
    }

    @FXML
    private void checkoutStudent() {
        LibraryModel selected = tableViewLbm.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String findID = new ValidatorInputFieldUtils().ValidateAllToUpperCase(codeStudentField.getText());
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
    }


    public void clear() {
        codeStudentField.setText("");
        nameStudentField.setText("");
        classStudentField.setText("");
        phoneNumberStudentField.setText("");
    }
}
