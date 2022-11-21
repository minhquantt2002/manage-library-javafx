package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.controllers.BorrowerController;
import btl_java.manage_library.utils.AlertWarningUtils;
import btl_java.manage_library.utils.ConnectionUtils;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.*;

import btl_java.manage_library.utils.ValidatorInputFieldUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;


public class BookController implements Initializable {
    @FXML
    private TextField codeBookField;
    @FXML
    private TextField categoryBookField;
    @FXML
    private TextField nameBookField;
    @FXML
    private TextField authorBookField;
    @FXML
    private TextField totalBookField;
    @FXML
    private TableView<BookModel> tableViewBook;
    @FXML
    private TableColumn<BookModel, String> stt;
    @FXML
    private TableColumn<BookModel, String> code;
    @FXML
    private TableColumn<BookModel, String> categoryBook;
    @FXML
    private TableColumn<BookModel, String> nameBook;
    @FXML
    private TableColumn<BookModel, String> authorBook;
    @FXML
    private TableColumn<BookModel, String> totalBook;
    @FXML
    private TableColumn<BookModel, String> remainBook;
    private final String stmtGetAll = "SELECT * FROM book";
    private final Connection connection = new ConnectionUtils().connectDB();

    private final Callback<TableView<BookModel>, TableRow<BookModel>> actionClick = setTextField -> {
        TableRow<BookModel> row = new TableRow<>();
        row.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                BookModel selected = row.getItem();
                codeBookField.setText(selected.getCodeBook().getValue());
                categoryBookField.setText(selected.getCategoryBook().getValue());
                nameBookField.setText(selected.getNameBook().getValue());
                authorBookField.setText(selected.getAuthorBook().getValue());
                totalBookField.setText(selected.getTotalBook().getValue());
            }
        });
        return row;
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stt.setCellValueFactory(cellData -> cellData.getValue().getStt());
        code.setCellValueFactory(cellData -> cellData.getValue().getCodeBook());
        categoryBook.setCellValueFactory(cellData -> cellData.getValue().getCategoryBook());
        nameBook.setCellValueFactory(cellData -> cellData.getValue().getNameBook());
        authorBook.setCellValueFactory(cellData -> cellData.getValue().getAuthorBook());
        totalBook.setCellValueFactory(cellData -> cellData.getValue().getTotalBook());
        remainBook.setCellValueFactory(cellData -> cellData.getValue().getRemainingBook());

        setDataTableViewBook(stmtGetAll);
        tableViewBook.setRowFactory(actionClick);
    }

    // Hàm set data cho bảng Book
    private void setDataTableViewBook(String stmt) {
        ObservableList<BookModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery(stmt);
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

    // Hàm thêm 1 book mới
    @FXML
    private void insertBook() {
        boolean isValid = new AlertWarningUtils().checkValidBook(codeBookField.getText(), categoryBookField.getText(), nameBookField.getText(), authorBookField.getText(), totalBookField.getText());
        if (!isValid) {
            return;
        }
        try {
            String stmt = "INSERT INTO book (code, category, name, author, total, remain) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1, new ValidatorInputFieldUtils().ValidateAllToUpperCase(codeBookField.getText()));
            preparedStatement.setString(2, new ValidatorInputFieldUtils().chuanhoa1(categoryBookField.getText()));
            preparedStatement.setString(3, new ValidatorInputFieldUtils().chuanhoa1(nameBookField.getText()));
            preparedStatement.setString(4, new ValidatorInputFieldUtils().ValidateFirstUpperCase(authorBookField.getText()));
            preparedStatement.setString(5, String.valueOf(Integer.parseInt(totalBookField.getText())));
            preparedStatement.setString(6, String.valueOf(Integer.parseInt(totalBookField.getText())));
            preparedStatement.executeUpdate();
            System.out.println("Create done a record: " + new BookModel("", codeBookField.getText(), categoryBookField.getText(),
                    nameBookField.getText(), authorBookField.getText(), totalBookField.getText()));
            setDataTableViewBook(stmtGetAll);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clear();
    }

    // Hàm sửa một quyển sách
    @FXML
    private void editBook() {
        BookModel selected = tableViewBook.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert warn = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) warn.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("images/library_icon.jpg"));
            warn.setTitle("Xác nhận");
            warn.setHeaderText("Xác nhận chỉnh sửa thông tin");
            Optional<ButtonType> option = warn.showAndWait();
            try {
                if (ButtonType.OK == option.get()) {
                    String Up = "UPDATE book set category = '" + categoryBookField.getText() +
                            "', name = '" + nameBookField.getText() + "', author = '" + authorBookField.getText() +
                            "', total='" + totalBookField.getText() + "', " +
                            "remain = '" + (Integer.parseInt(selected.getRemainingBook().getValue()) + Integer.parseInt(totalBookField.getText()) - Integer.parseInt(selected.getTotalBook().getValue())) + "'  where code='" + selected.getCodeBook().getValue() + "'";
                    try {
                        Connection connection = new ConnectionUtils().connectDB();
                        PreparedStatement preparedStatement = connection.prepareStatement(Up);
                        preparedStatement.executeUpdate();
                        System.out.println("Update done a record: ");
                    } catch (SQLException err) {
                        System.err.println("Update : " + err.getMessage());
                    }
                    setDataTableViewBook(stmtGetAll);
                }
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
            }
        }

    }

    // Hàm filter sách
    @FXML
    private void searchBook() {
        String find = "";
        String temp = "";
        if (!codeBookField.getText().equals("") && nameBookField.getText().equals("") && categoryBookField.getText().equals("") && authorBookField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().ValidateAllToUpperCase(codeBookField.getText());
            temp = "SELECT * FROM book WHERE code ='" + find + "'";
        } else if (codeBookField.getText().equals("") && !nameBookField.getText().equals("") && categoryBookField.getText().equals("") && authorBookField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().chuanhoa1(nameBookField.getText());
            temp = "SELECT * FROM book WHERE name ='" + find + "'";
        } else if (codeBookField.getText().equals("") && nameBookField.getText().equals("") && !categoryBookField.getText().equals("") && authorBookField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().chuanhoa1(categoryBookField.getText());
            temp = "SELECT * FROM book WHERE category ='" + find + "'";
        } else if (codeBookField.getText().equals("") && nameBookField.getText().equals("") && categoryBookField.getText().equals("") && !authorBookField.getText().equals("")) {
            find = new ValidatorInputFieldUtils().ValidateFirstUpperCase(authorBookField.getText());
            temp = "SELECT * FROM book WHERE author ='" + find + "'";
        }
        if (find.equals("")) {
            setDataTableViewBook(stmtGetAll);
        } else {
            setDataTableViewBook(temp);
        }
        clear();
    }

    // hàm xóa sách
    @FXML
    private void deleteBook(ActionEvent actionEvent) {
        BookModel selected = tableViewBook.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if(!selected.getTotalBook().getValue().equals(selected.getRemainingBook().getValue())){
                new AlertWarningUtils().showAlertWarning("Sách này chưa được trả hết!");
                return;
            }
            Alert warn = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc muốn xóa loai sách này? ");
            Optional<ButtonType> option = warn.showAndWait();
            try {
                if (ButtonType.OK == option.get()) {
                    String query = "DELETE FROM book WHERE code ='" + selected.getCodeBook().getValue() + "'";
                    try {
                        Connection conn = new ConnectionUtils().connectDB();
                        PreparedStatement pstm = conn.prepareStatement(query);
                        pstm.executeUpdate();
                        System.out.println("Delete done a record: " + selected);
                    } catch (SQLException err) {
                        err.printStackTrace();
                    }
                    setDataTableViewBook(stmtGetAll);
                }
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
            }
        }
    }

    // Hàm clear field nhập sách
    private void clear() {
        codeBookField.setText("");
        nameBookField.setText("");
        authorBookField.setText("");
        categoryBookField.setText("");
        totalBookField.setText("");
    }

}

