package btl_java.manage_library.controllers;

import btl_java.manage_library.MainApplication;
import btl_java.manage_library.models.LibraryModel;
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
    @FXML
    private TableColumn<LibraryModel, Date> timeIn;
    @FXML
    private TableColumn<LibraryModel, Date> timeOut;
    @FXML
    private TableColumn<LibraryModel, String> nameStudent;
    @FXML
    private TableColumn<LibraryModel, String> codeStudent;
    @FXML
    private TableColumn<LibraryModel, String> bookTook;
    public Button btnChangePassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        timeIn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
//        timeOut.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
//        nameStudent.setCellValueFactory(new PropertyValueFactory<>("nameStudent"));
//        codeStudent.setCellValueFactory(new PropertyValueFactory<>("codeStudent"));
//        bookTook.setCellValueFactory(new PropertyValueFactory<>("bookTook"));
        ObservableList<LibraryModel> list = getDataList();
        tableView.setItems(list);
        for (LibraryModel a : list) {
            System.out.println(a);
        }
    }

    public void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 702, 596);
        System.out.println(1);
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
        LibraryModel row1 = new LibraryModel(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "B20dccn553", "Quan", "Nhat ki trong tu");
        LibraryModel row2 = new LibraryModel(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "B20dccn553", "Quan", "Nhat ki trong tu");
        LibraryModel row3 = new LibraryModel(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "B20dccn553", "Quan", "Nhat ki trong tu");
        return FXCollections.observableArrayList(row1, row2, row3);
    }
}