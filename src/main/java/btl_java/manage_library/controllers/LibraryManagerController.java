package btl_java.manage_library.controllers;

import btl_java.manage_library.utils.ConnectionUtils;
import btl_java.manage_library.models.LibraryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class LibraryManagerController implements Initializable {
    @FXML
    public Button insertBtn;
    @FXML
    public TextField nameStfield;
    @FXML
    public TextField idStfield;
    @FXML
    public TextField classStfield;
    @FXML
    public TextField sdtStfield;
    @FXML
    private TableView<LibraryModel> tableView;
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

    @FXML
    // TODO: Quân
    public void initialize(URL url, ResourceBundle rb) {
        stt.setCellValueFactory(cellData -> cellData.getValue().getStt());
        codeStudent.setCellValueFactory(cellData -> cellData.getValue().getCodeStudent());
        nameStudent.setCellValueFactory(cellData -> cellData.getValue().getNameStudent());
        classStudent.setCellValueFactory(cellData -> cellData.getValue().getClassStudent());
        phoneNumber.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        timeIn.setCellValueFactory(cellData -> cellData.getValue().getTimeIn());
        timeOut.setCellValueFactory(cellData -> cellData.getValue().getTimeOut());
        setDataTableView();
    }

    public void setDataTableView() {
        Connection connection = new ConnectionUtils().connectDB();
        String stmt = "SELECT * FROM library_manager";
        ObservableList<LibraryModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery(stmt);
            while (resultSet.next()) {
                LibraryModel row = new LibraryModel(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(list);
    }


    /**
     * Tâm làm phần dưới
     * Những cái t để TODO ấy check thử xem
     **/


    //  TODO: Tâm + Quân
    public void insertStudent() {
        // Kiểm tra dữ liệu trước khi thêm sinh viên
        boolean valid = checkInput();
        if (!valid) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Chưa điền đủ thông tin", "", "Điền thông tin", ButtonType.CLOSE);
            warn.show();
        }
        String stmt = "INSERT INTO library_manager (student_code, full_name, class_name, phone_number, time_in, time_out) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = new ConnectionUtils().connectDB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1, idStfield.getText());
            preparedStatement.setString(2, nameStfield.getText());
            preparedStatement.setString(3, classStfield.getText());
            preparedStatement.setString(4, sdtStfield.getText());
            preparedStatement.setString(5, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
            preparedStatement.setString(6, "");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setDataTableView();

        // TODO: Tâm -> cần validate dữ liệu đầu vào. m nên làm ở hàm checkInput ở dưới
    }

    public boolean checkInput() {
        return !idStfield.getText().isEmpty() && !nameStfield.getText().isEmpty() && !classStfield.getText().isEmpty() && !sdtStfield.getText().isEmpty();
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
}
