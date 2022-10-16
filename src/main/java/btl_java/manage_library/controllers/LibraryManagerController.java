package btl_java.manage_library.controllers;

import btl_java.manage_library.models.LibraryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.temporal.ValueRange;
import java.util.Date;
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

        ObservableList<LibraryModel> list = getDataList();
        tableView.setItems(list);
    }

    private ObservableList<LibraryModel> getDataList() {
        LibraryModel test = new LibraryModel(1, "B20dccn553", "Quan1", "0333935933", "D20DCQCN01-B");
        LibraryModel test1 = new LibraryModel(2, "B20dccn553", "Quan2", "0333935933", "D20DCQCN01-B");
        LibraryModel test2 = new LibraryModel(3, "B20dccn553", "Quan3", "0333935933", "D20DCQCN01-B");
        LibraryModel test3 = new LibraryModel(4, "B20dccn553", "Quan4", "0333935933", "D20DCQCN01-B");
        LibraryModel test4 = new LibraryModel(5, "B20dccn553", "Quan5", "0333935933", "D20DCQCN01-B");
        LibraryModel test5 = new LibraryModel(6, "B20dccn553", "Quan6", "0333935933", "D20DCQCN01-B");
        LibraryModel test6 = new LibraryModel(7, "B20dccn553", "Quan7", "0333935933", "D20DCQCN01-B");
        LibraryModel test7 = new LibraryModel(8, "B20dccn553", "Quan8", "0333935933", "D20DCQCN01-B");
        LibraryModel test8 = new LibraryModel(9, "B20dccn553", "Quan9", "0333935933", "D20DCQCN01-B");
        LibraryModel test9 = new LibraryModel(10, "B20dccn553", "Quan10", "0333935933", "D20DCQCN01-B");
        LibraryModel test10 = new LibraryModel(11, "B20dccn553", "Quan11", "0333935933", "D20DCQCN01-B");
        LibraryModel test11 = new LibraryModel(12, "B20dccn553", "Quan12", "0333935933", "D20DCQCN01-B");
        return FXCollections.observableArrayList(test, test1, test2, test3, test4, test6, test7, test8, test9, test10, test11);
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
