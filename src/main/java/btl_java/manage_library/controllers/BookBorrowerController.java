package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BookBorrowerModel;
import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.utils.ConnectionUtils;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BookBorrowerController implements Initializable {
    @FXML
    private RadioButton borrowBook;
    @FXML
    private RadioButton returnBook;
    @FXML
    private TextField codeStudentField;
    @FXML
    private TextField classStudentField;
    @FXML
    private TextField nameStudentField;
    @FXML
    private TextField phoneNumberStudentField;
    @FXML
    private TableColumn<BookModel, String> sttBook;
    @FXML
    private TableColumn<BookModel, String> codeBook;
    @FXML
    private TableColumn<BookModel, String> nameBook;
    @FXML
    private TableColumn<BookModel, String> categoryBook;
    @FXML
    private TableColumn<BookModel, String> authorBook;
    @FXML
    private TableColumn<BookModel, String> remainBook;
    @FXML
    private TableColumn<BookBorrowerModel, String> sttStudentBorrowed;
    @FXML
    private TableColumn<BookBorrowerModel, String> codeStudentBorrowed;
    @FXML
    private TableColumn<BookBorrowerModel, String> nameStudentBorrowed;
    @FXML
    private TableColumn<BookBorrowerModel, String> classStudentBorrowed;
    @FXML
    private TableColumn<BookBorrowerModel, String> phoneNumberStudentBorrowed;
    @FXML
    private TableColumn<BookBorrowerModel, String> dateBorrowBook;
    @FXML
    private TableColumn<BookBorrowerModel, String> dateReturnBook;
    @FXML
    private TableView<BookModel> tableViewBook;
    @FXML
    private TableView<BookBorrowerModel> tableViewBorrower;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        borrowBook.setToggleGroup(toggleGroup);
        returnBook.setToggleGroup(toggleGroup);
        borrowBook.setSelected(true);

        sttStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getStt());
        codeStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getCodeStudent());
        nameStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getNameStudent());
        classStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getClassStudent());
        phoneNumberStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        dateBorrowBook.setCellValueFactory(cellData -> cellData.getValue().getBorrowDate());
        dateReturnBook.setCellValueFactory(cellData -> cellData.getValue().getReturnDate());

        sttBook.setCellValueFactory(cellData -> cellData.getValue().getStt());
        codeBook.setCellValueFactory(cellData -> cellData.getValue().getCode());
        nameBook.setCellValueFactory(cellData -> cellData.getValue().getNameBook());
        categoryBook.setCellValueFactory(cellData -> cellData.getValue().getCategoryBook());
        authorBook.setCellValueFactory(cellData -> cellData.getValue().getAuthorBook());
        remainBook.setCellValueFactory(cellData -> cellData.getValue().getRemainingBook());

        tableViewBook.setItems(new BookController().getFunctionSetTableViewBook());
        setDataTableViewBorrower();
    }

    private void setDataTableViewBorrower() {
        Connection connection = new ConnectionUtils().connectDB();
        String stmt = "SELECT * FROM book_borrower";
        ObservableList<BookBorrowerModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery(stmt);
            while (resultSet.next()) {
                BookBorrowerModel row = new BookBorrowerModel(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                row.setStt(Integer.toString(i));
                i++;
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableViewBorrower.setItems(list);
    }

    @FXML
    private void insertRecord() {

    }

    @FXML
    private void deleteStudent() {

    }

    public void clear() {

    }

    void refreshTableViewBook(ObservableList<BookModel> list) {
//        tableViewBook = new TableView<>(list);
        System.out.println(list);
    }
}
