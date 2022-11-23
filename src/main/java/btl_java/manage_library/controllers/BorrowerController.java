package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BorrowDetailModel;
import btl_java.manage_library.models.BorrowerModel;
import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.utils.AlertWarningUtils;
import btl_java.manage_library.utils.ConnectionUtils;
import btl_java.manage_library.utils.ValidatorInputFieldUtils;
import javafx.collections.*;
import javafx.event.ActionEvent;
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
    private Label titleBorrowDetail;
    @FXML
    private RadioButton buttonBorrowBook;
    @FXML
    private RadioButton buttonReturnBook;
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
    private TableView<BookModel> tableViewBook;
    @FXML
    private TableView<BorrowerModel> tableViewBorrower;
    @FXML
    private TableView<BorrowDetailModel> tableViewBorrowDetail;
    private final Connection connection = new ConnectionUtils().connectDB();
    private final ObservableList<BorrowDetailModel> borrowDetailList = FXCollections.observableArrayList();

    private final Callback<TableView<BookModel>, TableRow<BookModel>> doubleClickTableBook = bookModelTableView -> {
        TableRow<BookModel> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                titleBorrowDetail.setText("DS sách mượn");
                BookModel clickBook = row.getItem();
                System.out.println(clickBook);
                Alert confirmAddBook = new Alert(Alert.AlertType.CONFIRMATION);
                Stage stage = (Stage) confirmAddBook.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("images/library_icon.jpg"));
                confirmAddBook.setTitle("Xác nhận");
                confirmAddBook.setHeaderText("Xác nhận cho mượn sách");
                confirmAddBook.setContentText("Mã sách : " + clickBook.getCodeBook().getValue() + "\n" + "Tên sách: " + clickBook.getNameBook().getValue() + "\n" + "Thể loại sách: " + clickBook.getCategoryBook().getValue() + "\n" + "Tên tác giả: " + clickBook.getAuthorBook().getValue());
                Optional<ButtonType> option = confirmAddBook.showAndWait();
                option.ifPresent(result -> {
                    if (result == ButtonType.OK) {
                        addToTableBorrowDetails(clickBook);
                    }
                });
            }
        });
        return row;
    };
    private final Callback<TableView<BorrowerModel>, TableRow<BorrowerModel>> doubleClickTableBorrower = borrowerModelTableView -> {
        TableRow<BorrowerModel> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                BorrowerModel clickBook = row.getItem();
                titleBorrowDetail.setText("Ds sách mượn của sinh viên: " + clickBook.getNameStudent().getValue());
                System.out.println(clickBook);
                borrowDetailList.clear();
                setDataTableViewBorrowDetail(clickBook.getCodeStudent().getValue());
                codeStudentField.setText(clickBook.getCodeStudent().getValue());
                nameStudentField.setText(clickBook.getNameStudent().getValue());
                classStudentField.setText(clickBook.getClassStudent().getValue());
                phoneNumberStudentField.setText(clickBook.getPhoneNumber().getValue());
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
        nameDetail.setCellValueFactory(cellData -> cellData.getValue().getNameBook());

        setDataTableViewBook();
        setDataTableViewBorrower();

        tableViewBorrower.setRowFactory(doubleClickTableBorrower);
        tableViewBook.setRowFactory(doubleClickTableBook);

        tableViewBorrowDetail.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ToggleGroup toggle = new ToggleGroup();
        buttonBorrowBook.setToggleGroup(toggle);
        buttonReturnBook.setToggleGroup(toggle);
        buttonBorrowBook.setSelected(true);
    }

    private void addToTableBorrowDetails(BookModel book) {
        for (BorrowDetailModel bd : borrowDetailList) {
            if (bd.getCodeBook().getValue().equals(book.getCodeBook().getValue())) {
                return;
            }
        }
        BorrowDetailModel newRecord = new BorrowDetailModel(Integer.toString(borrowDetailList.size() + 1), book.getCodeBook().getValue(), book.getNameBook().getValue(), "", "");
        System.out.println(newRecord);
        borrowDetailList.add(newRecord);
        tableViewBorrowDetail.setItems(borrowDetailList);
    }

    private void setDataTableViewBorrowDetail(String student_code) {
        ObservableList<BorrowDetailModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery(
                    "SELECT borrowed_book_detail.id, borrowed_book_detail.book_code, book.name, borrowed_book_detail.borrower_id, borrowed_book_detail.borrowed, borrowed_book_detail. returned "
                            + "FROM  borrowed_book_detail  INNER JOIN book ON borrowed_book_detail.book_code = book.code WHERE borrower_id = '" + student_code + "'");
            while (resultSet.next()) {
                BorrowDetailModel row = new BorrowDetailModel(
                        Integer.toString(i),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                row.setId(resultSet.getString(1));
                row.setStudent_code(resultSet.getString(4));
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
            resultSet = connection.createStatement().executeQuery("SELECT * FROM  book WHERE remain > 0");
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
            System.out.println(e.getMessage());
        }
        tableViewBook.setItems(list);
    }

    private void setDataTableViewBorrower() {
        ObservableList<BorrowerModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery(
                    "select student_code, full_name, class_name, phone_number from borrower inner join borrowed_book_detail on borrower_id = student_code "
                            + "group by student_code, full_name, class_name, phone_number order by returned"
            );
            while (resultSet.next()) {
                BorrowerModel row = new BorrowerModel(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                row.setStt(Integer.toString(i));
                i++;
                list.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        tableViewBorrower.setItems(list);
    }

    @FXML
    private void insertBorrower() {
        boolean type = buttonBorrowBook.isSelected();
        boolean isValid = new AlertWarningUtils().checkValidStudent(codeStudentField.getText(), nameStudentField.getText(), classStudentField.getText(), phoneNumberStudentField.getText());
        if (!isValid) {
            return;
        }
        if (!type) {
            if (borrowDetailList.size() == 0) {
                returnBook();
            } else {
                new AlertWarningUtils().
                        showAlertWarning("Với tác vụ trả sách, bảng thông tin sách mượn của sinh viên này cần được hiện thị!");
            }
            return;
        }
        if (borrowDetailList.size() != 0) {
            borrowBook();
            return;
        }
        new AlertWarningUtils().showAlertWarning("Thiếu trường sách mượn của sinh viên này!");
    }

    private void borrowBook() {
        try {
            ResultSet isPresent = connection.createStatement().executeQuery("SELECT student_code FROM borrower WHERE student_code ='" + codeStudentField.getText() + "'");
            if (!isPresent.next()) {
                String stmtInsertBorrower = "INSERT INTO borrower (student_code, full_name, class_name, phone_number) VALUES (?, ?, ?, ?)";
                PreparedStatement ppsBorrower = connection.prepareStatement(stmtInsertBorrower);
                ppsBorrower.setString(1, new ValidatorInputFieldUtils().ValidateAllToUpperCase(codeStudentField.getText()));
                ppsBorrower.setString(2, new ValidatorInputFieldUtils().ValidateFirstUpperCase(nameStudentField.getText()));
                ppsBorrower.setString(3, new ValidatorInputFieldUtils().ValidateAllToUpperCase(classStudentField.getText()));
                ppsBorrower.setString(4, phoneNumberStudentField.getText());
                ppsBorrower.executeUpdate();
                System.out.println("Create done a record: " + new BorrowerModel(codeStudentField.getText(), nameStudentField.getText(), classStudentField.getText(), phoneNumberStudentField.getText()));
            }

            String borrowedTime = DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy").format(LocalDateTime.now());
            for (BorrowDetailModel newRecord : borrowDetailList) {
                String stmtDetail = "INSERT INTO borrowed_book_detail (book_code, borrower_id, borrowed, returned) VALUES (?, ?, ?, ?)";
                ResultSet getRemainBook = connection.createStatement().executeQuery("SELECT remain FROM book WHERE code ='" + newRecord.getCodeBook().getValue() + "'");
                int remain = 0;
                if (getRemainBook.next()) {
                    remain = Integer.parseInt(getRemainBook.getString(1)) - 1;
                }
                String stmtUpdateBook = "UPDATE book SET remain = " + remain + " WHERE code = '" + newRecord.getCodeBook().getValue() + "'";
                PreparedStatement ppsDetail = connection.prepareStatement(stmtDetail);
                PreparedStatement ppsBook = connection.prepareStatement(stmtUpdateBook);
                ppsDetail.setString(1, newRecord.getCodeBook().getValue());
                ppsDetail.setString(2, codeStudentField.getText());
                ppsDetail.setString(3, borrowedTime);
                ppsDetail.setString(4, "");
                ppsDetail.executeUpdate();
                ppsBook.executeUpdate();
            }
            borrowDetailList.clear();
            tableViewBorrowDetail.setItems(borrowDetailList);
            setDataTableViewBorrower();
            clearField();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void returnBook() {
        ObservableList<BorrowDetailModel> list = tableViewBorrowDetail.getSelectionModel().getSelectedItems();
        if (list.size() == 0) {
            new AlertWarningUtils().showAlertWarning("Vui lòng chọn sách trả ! ");
            return;
        }
        try {
            String returnedTime = DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy").format(LocalDateTime.now());
            for (BorrowDetailModel aRecord : list) {
                if(!aRecord.getReturnDate().getValue().equals("")) continue;
                ResultSet getRemainBook = connection.createStatement().executeQuery("SELECT remain FROM book WHERE code ='" + aRecord.getCodeBook().getValue() + "'");
                int remain = 0;
                if (getRemainBook.next()) {
                    remain = Integer.parseInt(getRemainBook.getString(1)) + 1;
                }
                String stmtDetail = "UPDATE borrowed_book_detail SET returned = ? WHERE id = ? and returned = ''";
                String stmtUpdateBook = "UPDATE book SET remain = " + remain + " WHERE code = '" + aRecord.getCodeBook().getValue() + "'";
                PreparedStatement ppsBook = connection.prepareStatement(stmtUpdateBook);
                PreparedStatement ppsDetail = connection.prepareStatement(stmtDetail);
                ppsDetail.setString(1, returnedTime);
                ppsDetail.setString(2, aRecord.getId().getValue());
                ppsDetail.executeUpdate();
                ppsBook.executeUpdate();
            }
            String studentCode = list.get(0).getStudent_code().getValue();
            setDataTableViewBorrower();
            tableViewBorrowDetail.setItems(null);
            setDataTableViewBorrowDetail(studentCode);
            setDataTableViewBook();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearField() {
        codeStudentField.setText("");
        nameStudentField.setText("");
        classStudentField.setText("");
        phoneNumberStudentField.setText("");
        setDataTableViewBook();
        setDataTableViewBorrower();
        setDataTableViewBorrowDetail(null);
    }

    public void btnSearchStudent(ActionEvent actionEvent) {
        if(!codeStudentField.getText().equals("")){
            ObservableList<BorrowerModel> list = FXCollections.observableArrayList();
            ResultSet resultSet;
            try {
                int i = 1;
                resultSet = connection.createStatement().executeQuery(
                        "select student_code, full_name, class_name, phone_number from borrower inner join borrowed_book_detail on borrower_id = student_code where student_code ='"+codeStudentField.getText()+"' "
                                + "group by student_code, full_name, class_name, phone_number order by returned"
                );
                while (resultSet.next()) {
                    BorrowerModel row = new BorrowerModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                    row.setStt(Integer.toString(i));
                    i++;
                    list.add(row);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            tableViewBorrower.setItems(list);
        }

    }
}
