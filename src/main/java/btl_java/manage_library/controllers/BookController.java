package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.utils.ConnectionUtils;
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
import java.util.ResourceBundle;


public class BookController implements Initializable {
    @FXML
    public TextField categoryBookField;
    @FXML
    public TextField nameBookField;
    @FXML
    public TextField authorBookField;
    @FXML
    private TableView<BookModel> tableView;
    @FXML
    private TableColumn<BookModel, String> stt;
    @FXML
    private TableColumn<BookModel, String> categoryBook;
    @FXML
    private TableColumn<BookModel, String> nameBook;
    @FXML
    private TableColumn<BookModel, String> authorBook;
    @FXML
    private TableColumn<BookModel, String> totalBook;
    @FXML
    private TableColumn<BookModel, String> remainingBook;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        stt.setCellValueFactory(cellData -> cellData.getValue().getStt());
        categoryBook.setCellValueFactory(cellData -> cellData.getValue().getCategoryBook());
        nameBook.setCellValueFactory(cellData -> cellData.getValue().getNameBook());
        authorBook.setCellValueFactory(cellData -> cellData.getValue().getAuthorBook());
        totalBook.setCellValueFactory(cellData -> cellData.getValue().getTotalBook());
        remainingBook.setCellValueFactory(cellData -> cellData.getValue().getRemainingBook());
        setDataTableView();
    }
    public void setDataTableView() {
        Connection connection = new ConnectionUtils().connectDB();
        String stmt = "SELECT * FROM book";
        ObservableList<BookModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery(stmt);
            while (resultSet.next()) {
                BookModel row = new BookModel(
                        Integer.toString(i),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                i++;
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(list);
    }
    //============================INSERTBOOK==================================
    public void insertBook() {
        boolean valid = checkInput();
        if(!valid){
            Alert warn = this.createAlert(Alert.AlertType.WARNING,"chưa điền đủ thông tin!","","Điền thông tin",ButtonType.CLOSE);
            warn.show();
            return;
        }
        else {
            String stmt = "INSERT INTO book (category, name, author,total,remain) VALUES (?, ?, ?, ?,?)";
            Connection connection = new ConnectionUtils().connectDB();
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(stmt);
                preparedStatement.setString(1,categoryBookField.getText());
                preparedStatement.setString(2,nameBookField.getText());
                preparedStatement.setString(3,authorBookField.getText());
                preparedStatement.setString(4,totalBook.getText());
                preparedStatement.setString(5,remainingBook.getText());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public boolean checkInput() {
        return !categoryBookField.getText().isEmpty() && !nameBookField.getText().isEmpty() && !authorBookField.getText().isEmpty();
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

    //============================EDITBOOK==================================
    public void editBook() {
    }
    //============================SEARCHBOOK==================================
    public void searchBook() {
    }
}

