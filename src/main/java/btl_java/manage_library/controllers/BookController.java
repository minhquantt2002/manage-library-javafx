package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.utils.ConnectionUtils;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.*;

import btl_java.manage_library.utils.ValidatorInputFieldUtils;


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
    }


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

    @FXML
    private void insertBook() {
        boolean valid = checkInput();
        if (!valid) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Chưa điền đủ thông tin!", "", "Điền thông tin", ButtonType.CLOSE);
            warn.show();
            return;
        }
        String stmt = "INSERT INTO book (code, category, name, author, total, remain) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = new ConnectionUtils().connectDB();
        try {
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

    @FXML
    private void editBook() {
        BookModel selected = tableViewBook.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Bạn có chắc muốn chỉnh sửa thông tin về quyển sách này ? ", "", "sửa thông tin sách ", ButtonType.CLOSE);
            Optional<ButtonType> option = warn.showAndWait();
            try {
                if (ButtonType.OK == option.get()) {
                    setTextField(selected);
                    String pop = "DELETE FROM book WHERE code ='" + selected.getCodeBook().getValue() + "'";
                    try {
                        Connection connection = new ConnectionUtils().connectDB();
                        PreparedStatement preparedStatement = connection.prepareStatement(pop);
                        preparedStatement.executeUpdate();
                        System.out.println("Delete done a record: " + selected);
                    } catch (SQLException err) {
                        System.err.println("Delete : " + err.getMessage());
                    }
                    setDataTableViewBook(stmtGetAll);
                }
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
            }
        }

    }

    private void setTextField(BookModel k) {
        codeBookField.setText(k.getCodeBook().getValue());
        categoryBookField.setText(k.getCategoryBook().getValue());
        nameBookField.setText(k.getNameBook().getValue());
        authorBookField.setText(k.getAuthorBook().getValue());
        totalBookField.setText(k.getTotalBook().getValue());
    }

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

    private boolean checkInput() {
        return !categoryBookField.getText().isEmpty() && !nameBookField.getText().isEmpty() && !authorBookField.getText().isEmpty()
                && !totalBookField.getText().isEmpty() && !codeBookField.getText().isEmpty();
    }

    private Alert createAlert(Alert.AlertType type, String content, String header, String title, ButtonType... buttonTypes) {
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


    public void clear() {
        codeBookField.setText("");
        nameBookField.setText("");
        authorBookField.setText("");
        categoryBookField.setText("");
        totalBookField.setText("");
    }

    public void bookDelete(ActionEvent actionEvent) {
        BookModel selected = tableViewBook.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Bạn có chắc muốn xóa loai sách này  ? ", "", "Delete ", ButtonType.CLOSE);
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
                        System.err.println("Delete : " + err.getMessage());
                    }
                    setDataTableViewBook(stmtGetAll);
                }
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
            }
        }
    }
}

