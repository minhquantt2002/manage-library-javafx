package btl_java.manage_library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class BorrowingBookController implements Initializable {
    @FXML
    public Button insertBtn;
    public RadioButton borrowBook;
    public RadioButton returnBook;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        borrowBook.setToggleGroup(toggleGroup);
        returnBook.setToggleGroup(toggleGroup);
    }


    public void clear() {

    }

    public void deleteStudent() {

    }

    public void insertRecord() {
    }
}
