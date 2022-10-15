package btl_java.manage_library.controllers;

import btl_java.manage_library.models.LibraryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LibraryManagerController implements Initializable {
    public Button insertBtn;
    public TextField nameStfield;
    public TextField idStfield;
    public TextField classStfield;
    public TextField sdtStfield;
    @FXML
    private TableView<LibraryModel> tableView;
    //    @FXML
//    private TableColumn<LibraryModel, Date> timeIn;
//    @FXML
//    private TableColumn<LibraryModel, Date> timeOut;
    @FXML
    private TableColumn<LibraryModel, String> nameStudent;
    @FXML
    private TableColumn<LibraryModel, String> codeStudent;
    @FXML
    private TableColumn<LibraryModel, String> bookTook;
    public Button btnChangePassword;

    @FXML
    // TODO: Quân
    public void initialize(URL url, ResourceBundle rb) {
        // set theo getter ấy
        codeStudent.setCellValueFactory(cellData -> cellData.getValue().getCodeStudent());
        nameStudent.setCellValueFactory(cellData -> cellData.getValue().getNameStudent());
        bookTook.setCellValueFactory(cellData -> cellData.getValue().getBookTook());

        ObservableList<LibraryModel> list = getDataList();
        tableView.setItems(list);

        for (LibraryModel a : list) {
            System.out.println(a);
        }
    }
    private ObservableList<LibraryModel> getDataList() {
//        LibraryModel row1 = new LibraryModel(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "B20dccn553", "Quan", "Nhat ki trong tu");
//        LibraryModel row2 = new LibraryModel(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "B20dccn553", "Quan", "Nhat ki trong tu");
//        LibraryModel row3 = new LibraryModel(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "B20dccn553", "Quan", "Nhat ki trong tu");
        LibraryModel test = new LibraryModel("B20dccn553", "Quan1", "Nhat ki trong tu1");
        LibraryModel test1 = new LibraryModel("B20dccn553", "Quan2", "Nhat ki trong tu2");
        LibraryModel test2 = new LibraryModel("B20dccn553", "Quan3", "Nhat ki trong tu3");
        return FXCollections.observableArrayList(test, test1, test2);
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
