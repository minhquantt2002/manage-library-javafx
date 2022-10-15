package btl_java.manage_library.controllers;

import btl_java.manage_library.MainApplication;
import btl_java.manage_library.models.LibraryModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
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
    public void initialize(URL url, ResourceBundle rb) {
        // set theo getter ấy
        codeStudent.setCellValueFactory(cellData -> cellData.getValue().getCodeStudent());
        nameStudent.setCellValueFactory(cellData -> cellData.getValue().getNameStudent());
        bookTook.setCellValueFactory(cellData -> cellData.getValue().getBookTook());
        //
        ObservableList<LibraryModel> list = getDataList();
        tableView.setItems(list);
//        timeIn.setCellValueFactory(cell -> (ObservableValue<Date>) cell.getValue().getTimeIn());
//        codeStudent.setCellValueFactory(cell -> (ObservableValue<String>) cell.getValue().getCodeStudent());
//        nameStudent.setCellValueFactory(cell -> (ObservableValue<String>) cell.getValue().getTimeOut());
//        bookTook.setCellValueFactory(cell -> cell.getValue().getBookTook());
//        timeOut.setCellValueFactory(cell -> (ObservableValue<Date>) cell.getValue().getTimeOut());

//        timeOut.setCellValueFactory(new PropertyValueFactory<>("timeOut"));

        for (LibraryModel a : list) {
            System.out.println(a);
        }
    }

    public void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 702, 596);
        stage.setScene(scene);
        stage.setTitle("Quản lí thư viện");
        stage.show();
    }

    public void ChangePassword(ActionEvent event) {

    }

    public void btnDialog() {
        TextInputDialog log = new TextInputDialog();
        log.setTitle("Nhập mã sinh viên");
        log.setHeaderText("Nhập mã sinh viên");
        log.setContentText("MSV");
        Optional<String> result = log.showAndWait();
        System.out.println(result);

    }

    public void tableViewManage() {
//        ObservableList<LibraryModel> list = getDataList();
//        tableView.setItems(list);
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
}