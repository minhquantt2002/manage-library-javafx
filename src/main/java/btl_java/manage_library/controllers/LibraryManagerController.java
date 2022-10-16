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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LibraryManagerController implements Initializable {
    public Button insertBtn;
    public TextField nameStfield;
    public TextField idStfield;
    public TextField classStfield;
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
        String statement = "SELECT * FROM library_manager";
        ObservableList<LibraryModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery(statement);
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
//        ObservableList<LibraryModel> list = FXCollections.observableArrayList(test, test1, test2, test3, test4, test6, test7, test8, test9, test10, test11);
        tableView.setItems(list);
    }


    /**
     * Tâm làm phần dưới
     * Những cái t để TODO ấy check thử xem
     **/


    //  TODO: Tâm
    public void insertStudent() {
        // Kiểm tra dữ liệu trước khi thêm sinh viên
        boolean valid = checkInput();
        if (!valid) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Chưa điền đủ thông tin", "", "Điền thông tin", ButtonType.CLOSE);
            warn.show();
        }
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
