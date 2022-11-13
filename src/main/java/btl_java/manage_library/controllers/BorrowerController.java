package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BorrowDetailModel;
import btl_java.manage_library.models.BorrowerModel;
import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.utils.ConnectionUtils;
import btl_java.manage_library.utils.ValidatorInputFieldUtils;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BorrowerController implements Initializable {
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
    private TableColumn<BorrowerModel, String> sttStudentBorrowed;
    @FXML
    private TableColumn<BorrowerModel, String> codeStudentBorrowed;
    @FXML
    private TableColumn<BorrowerModel, String> nameStudentBorrowed;
    @FXML
    private TableColumn<BorrowerModel, String> classStudentBorrowed;
    @FXML
    private TableColumn<BorrowerModel, String> phoneNumberStudentBorrowed;
    @FXML
    private TableColumn<BorrowerModel, String> dateBorrowBook;
    @FXML
    private TableColumn<BorrowerModel, String> dateReturnBook;
    @FXML
    private TableColumn<BorrowDetailModel, String> sttDetail;
    @FXML
    private TableColumn<BorrowDetailModel, String> codeDetail;
    @FXML
    private TableColumn<BorrowDetailModel, String> nameDetail;
    @FXML
    private TableColumn<BorrowDetailModel, String> totalDetail;
    @FXML
    private TableView<BookModel> tableViewBook;
    @FXML
    private TableView<BorrowerModel> tableViewBorrower;
    @FXML
    private TableView<BorrowDetailModel> tableViewBorrowDetail;
    private final Connection connection = new ConnectionUtils().connectDB();
    private final ObservableList<BorrowDetailModel> borrowDetailList = FXCollections.observableArrayList();

    private final Callback<TableView<BookModel>, TableRow<BookModel>> doubleClick = bookModelTableView -> {
        TableRow<BookModel> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                BookModel clickBook = row.getItem();
                System.out.println(clickBook);
                while (true) {
                    TextInputDialog inputQuantityBook = new TextInputDialog();
                    inputQuantityBook.setHeaderText(null);
                    inputQuantityBook.setGraphic(null);
                    Stage stage = (Stage) inputQuantityBook.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("images/library_icon.jpg"));
                    inputQuantityBook.setContentText("Số lượng: ");
                    Optional<String> result = inputQuantityBook.showAndWait();
                    try {
                        result.ifPresent(input -> {
                            System.out.println(Integer.parseInt(input));
                            addToTableBorrowDetails(clickBook, input);
                        });
                        break;
                    } catch (Exception e) {
                        System.out.println("Input is invalid integer");
                    }
                }
            }
        });
        return row;
    };

    public void initialize(URL url, ResourceBundle resourceBundle) {
        sttStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getStt());
        codeStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getCodeStudent());
        nameStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getNameStudent());
        classStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getClassStudent());
        phoneNumberStudentBorrowed.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        dateBorrowBook.setCellValueFactory(cellData -> cellData.getValue().getBorrowDate());
        dateReturnBook.setCellValueFactory(cellData -> cellData.getValue().getReturnDate());

        sttBook.setCellValueFactory(cellData -> cellData.getValue().getStt());
        codeBook.setCellValueFactory(cellData -> cellData.getValue().getCodeBook());
        nameBook.setCellValueFactory(cellData -> cellData.getValue().getNameBook());
        categoryBook.setCellValueFactory(cellData -> cellData.getValue().getCategoryBook());
        authorBook.setCellValueFactory(cellData -> cellData.getValue().getAuthorBook());
        remainBook.setCellValueFactory(cellData -> cellData.getValue().getRemainingBook());

        sttDetail.setCellValueFactory(cellData -> cellData.getValue().getStt());
        codeDetail.setCellValueFactory(cellData -> cellData.getValue().getCodeBook());
        nameDetail.setCellValueFactory(cellData -> cellData.getValue().getCodeBook());
        totalDetail.setCellValueFactory(cellData -> cellData.getValue().getTotalBook());

        setDataTableViewBook();
        setDataTableViewBorrower();

        tableViewBook.setRowFactory(doubleClick);

        ToggleGroup toggle = new ToggleGroup();
        borrowBook.setToggleGroup(toggle);
        returnBook.setToggleGroup(toggle);
        borrowBook.setSelected(true);
//        borrowBook.setOnAction(actionEvent -> {
//            if (borrowBook.isSelected() && returnBook.isSelected()) {
//                returnBook.setSelected(false);
//                System.out.println(tableViewBook.getRowFactory());
//                tableViewBook.setRowFactory(doubleClick);
//            }
//        });
//        returnBook.setOnAction(actionEvent -> {
//            if (borrowBook.isSelected() && returnBook.isSelected()) {
//                borrowBook.setSelected(false);
//                tableViewBook.setRowFactory(null);
//                System.out.println(tableViewBook.getRowFactory());
//            }
//        });
    }

    private void addToTableBorrowDetails(BookModel book, String quantity) {
        BorrowDetailModel newRecord = new BorrowDetailModel(Integer.toString(borrowDetailList.size() + 1), book.getCodeBook().getValue(), book.getNameBook().getValue(), quantity);
        System.out.println(newRecord);
        borrowDetailList.add(newRecord);
        tableViewBorrowDetail.setItems(borrowDetailList);
    }

    private void setDataTableViewBorrowDetail() {
        ObservableList<BorrowDetailModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery("SELECT * FROM  borrowed_book_detail");
            while (resultSet.next()) {
                BorrowDetailModel row = new BorrowDetailModel(Integer.toString(i), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                i++;
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableViewBorrowDetail.setItems(list);
    }

    private void setDataTableViewBook() {
        ObservableList<BookModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery("SELECT * FROM  book");
            while (resultSet.next()) {
                BookModel row = new BookModel(Integer.toString(i), resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
                row.setRemainBook(resultSet.getString(6));
                i++;
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableViewBook.setItems(list);
    }

    private void setDataTableViewBorrower() {
        ObservableList<BorrowerModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery("SELECT * FROM book_borrower");
            while (resultSet.next()) {
                BorrowerModel row = new BorrowerModel(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
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
    private void insertBorrower() {
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
            System.out.println("Create done a record: " + new BorrowerModel(codeStudentField.getText(), nameStudentField.getText(), classStudentField.getText(), phoneNumberStudentField.getText(), borrowedTime, ""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteStudent() {

    }

    public void clear() {

    }
}
