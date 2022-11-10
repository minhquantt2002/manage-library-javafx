package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BookBorrowerModel;
import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.models.LibraryModel;
import btl_java.manage_library.utils.ConnectionUtils;
import btl_java.manage_library.utils.ValidatorInputFieldUtils;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private TableColumn<BookBorrowerModel, String> sttBorrow;
    @FXML
    private TableColumn<BookBorrowerModel, String> codeBookBorrow;
    @FXML
    private TableColumn<BookBorrowerModel, String> nameBookBorrow;
    @FXML
    private TableColumn<BookBorrowerModel, String> totalBorrow;
    @FXML
    private TableView<BookModel> tableViewBook;
    @FXML
    private TableView<BookBorrowerModel> tableViewBorrower;
    @FXML
    private TableView<BookModel> tableViewBookBorrow;
    Connection connection = new ConnectionUtils().connectDB();
    String stmtQueryAll = "SELECT * FROM book_borrower";

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
        setDataTableViewBorrower(stmtQueryAll);

        tableViewBook.setRowFactory(action -> {
            TableRow<BookModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    BookModel doubleBook = row.getItem();

                    System.out.println(doubleBook);
                }
            });
            return row;
        });
    }

    private void setDataTableViewBorrower(String stmt) {
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
        String type = borrowBook.isSelected() ? "Borrow" : "Return";
        BookModel positionBook = tableViewBook.getSelectionModel().getSelectedItem();
        try {
            String stmt = "INSERT INTO book_borrower (student_code, full_name, class_name, phone_number, borrowed, returned) VALUES (?, ?, ?, ?, ?, ?)";
            String borrowedTime = DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy").format(LocalDateTime.now());
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1, new ValidatorInputFieldUtils().ValidateAllToUpperCase(codeStudentField.getText()));
            preparedStatement.setString(2, new ValidatorInputFieldUtils().ValidateFirstUpperCase(nameStudentField.getText()));
            preparedStatement.setString(3, new ValidatorInputFieldUtils().ValidateAllToUpperCase(classStudentField.getText()));
            preparedStatement.setString(4, phoneNumberStudentField.getText());
            preparedStatement.setString(5, borrowedTime);
            preparedStatement.setString(6, "");
            preparedStatement.executeUpdate();
            System.out.println("Create done a record: " + new BookBorrowerModel(codeStudentField.getText(), nameStudentField.getText(),
                    classStudentField.getText(), phoneNumberStudentField.getText(), borrowedTime, ""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteStudent() {

    }

    public void clear() {

    }

    void refreshTableViewBook(ObservableList<BookModel> list) {
        tableViewBook = new TableView<>(list);
        System.out.println(list);
    }
}
