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
    private TableColumn<BorrowDetailModel, String> dateBorrowBook;
    @FXML
    private TableColumn<BorrowDetailModel, String> dateReturnBook;
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
        BorrowDetailModel newRecord = new BorrowDetailModel(
                Integer.toString(borrowDetailList.size() + 1),
                book.getCodeBook().getValue(),
                book.getNameBook().getValue(),
                quantity,
                "",
                ""
        );
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
                BorrowDetailModel row = new BorrowDetailModel(
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
        tableViewBorrowDetail.setItems(list);
    }

    private void setDataTableViewBook() {
        ObservableList<BookModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery("SELECT * FROM  book");
            while (resultSet.next()) {
                BookModel row = new BookModel(
                        Integer.toString(i),
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
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
            resultSet = connection.createStatement().executeQuery("SELECT * FROM borrower");
            while (resultSet.next()) {
                BorrowerModel row = new BorrowerModel(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
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
    private void insertBorrower() {
        boolean type = borrowBook.isSelected();
        if (!type) {
            return;
        }
        try {
            String stmtInsertBorrower = "INSERT INTO borrower (student_code, full_name, class_name, phone_number) VALUES (?, ?, ?, ?)";
            PreparedStatement ppsBorrower = connection.prepareStatement(stmtInsertBorrower);
            ppsBorrower.setString(1, new ValidatorInputFieldUtils().ValidateAllToUpperCase(codeStudentField.getText()));
            ppsBorrower.setString(2, new ValidatorInputFieldUtils().ValidateFirstUpperCase(nameStudentField.getText()));
            ppsBorrower.setString(3, new ValidatorInputFieldUtils().ValidateAllToUpperCase(classStudentField.getText()));
            ppsBorrower.setString(4, phoneNumberStudentField.getText());
            ppsBorrower.executeUpdate();
            System.out.println("Create done a record: " + new BorrowerModel(codeStudentField.getText(), nameStudentField.getText(), classStudentField.getText(), phoneNumberStudentField.getText()));

//            String codeStudent = connection.createStatement().executeQuery("SELECT code_student from borrower WHERE ")
            String borrowedTime = DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy").format(LocalDateTime.now());
            String stmtDetail = "INSERT INTO borrowed_book_detail (book_code, borrower_id, quantity, borrowed, returned) VALUES (?, ?, ?, ?, ?)";
            for (BorrowDetailModel newRecord : borrowDetailList) {
                PreparedStatement ppsDetail = connection.prepareStatement(stmtDetail);
                ppsDetail.setString(1, newRecord.getCodeBook().getValue());
                ppsDetail.setString(2, codeStudentField.getText());
                ppsDetail.setString(3, newRecord.getTotalBook().getValue());
                ppsDetail.setString(4, borrowedTime);
                ppsDetail.setString(5, "");
                ppsDetail.executeUpdate();
            }
            borrowDetailList.clear();
            tableViewBorrowDetail.setItems(borrowDetailList);
            setDataTableViewBorrower();
            clearField();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void deleteStudent() {

    }

    @FXML
    private void clearField() {
        codeStudentField.setText("");
        nameStudentField.setText("");
        classStudentField.setText("");
        phoneNumberStudentField.setText("");
    }
}
