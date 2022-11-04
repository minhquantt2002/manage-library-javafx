package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BookModel;
import btl_java.manage_library.models.LibraryModel;
import btl_java.manage_library.utils.ConnectionUtils;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.beans.Observable;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import btl_java.manage_library.utils.chuanhoa;


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
    private final String stmtQuery="SELECT * FROM book";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stt.setCellValueFactory(cellData -> cellData.getValue().getStt());
        code.setCellValueFactory(cellData -> cellData.getValue().getCode());
        categoryBook.setCellValueFactory(cellData -> cellData.getValue().getCategoryBook());
        nameBook.setCellValueFactory(cellData -> cellData.getValue().getNameBook());
        authorBook.setCellValueFactory(cellData -> cellData.getValue().getAuthorBook());
        totalBook.setCellValueFactory(cellData -> cellData.getValue().getTotalBook());
        remainBook.setCellValueFactory(cellData -> cellData.getValue().getRemainingBook());
        tableViewBook.setItems(setDataTableViewBook(stmtQuery));
    }

    // Hàm set dữ liệu cho bảng (Sách)
    private ObservableList<BookModel> setDataTableViewBook(String stmtQuery) {
        Connection connection = new ConnectionUtils().connectDB();
//        String stmt = "SELECT * FROM book";
        ObservableList<BookModel> list = FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery(stmtQuery);
            while (resultSet.next()) {
                BookModel row = new BookModel(
                        Integer.toString(i),
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
//                row.setStt(Integer.toString(i));
                row.setRemainBook(resultSet.getString(6));
                i++;
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm tạo một bản ghi mới trong database (thêm 1 sách mới)
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
            preparedStatement.setString(1, new chuanhoa().chuanhoaAll(codeBookField.getText()));
            preparedStatement.setString(2, new  chuanhoa().chuanhoa1(categoryBookField.getText()));
            preparedStatement.setString(3, new  chuanhoa().chuanhoa1(nameBookField.getText()));
            preparedStatement.setString(4, new chuanhoa().chuanhoaFirst(authorBookField.getText()));
            preparedStatement.setString(5, String.valueOf(Integer.parseInt(totalBookField.getText())));
            preparedStatement.setString(6, String.valueOf(Integer.parseInt(totalBookField.getText())));
            preparedStatement.executeUpdate();
            System.out.println("Create done a record: " + new BookModel("",codeBookField.getText(), categoryBookField.getText(),
                    nameBookField.getText(), authorBookField.getText(), totalBookField.getText()));
            ObservableList<BookModel> list = setDataTableViewBook(stmtQuery);
            tableViewBook.setItems(list);
//            new BookBorrowerController().refreshTableViewBook(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clear();
    }
//======================================EDITBOOK==========================================
    // Hàm sửa thông tin một quyền sách
    @FXML
    private void editBook() {
//        ObservableList<BookModel> list = setDataTableViewBook();
//        tableViewBook.setItems(list);
//        new BookBorrowerController().refreshTableViewBook(list);
        BookModel selected = tableViewBook.getSelectionModel().getSelectedItem();
        if(selected != null){
            Alert warn = this.createAlert(Alert.AlertType.WARNING, "Bạn có chắc muốn chỉnh sửa thông tin về quyển sách này ? ", "", "sửa thông tin sách ", ButtonType.CLOSE);
            Optional<ButtonType> option = warn.showAndWait();
            try{
                if (ButtonType.OK == option.get()) {
                    setTextField(selected);
                    String pop = "DELETE FROM book WHERE code ='" + selected.getCode().getValue() + "'";
                    try {
                        Connection connection = new ConnectionUtils().connectDB();
                        PreparedStatement preparedStatement = connection.prepareStatement(pop);
                        preparedStatement.executeUpdate();
                        System.out.println("Delete done a record: " + selected);
                    } catch (SQLException err) {
                        System.err.println("Delete : " + err.getMessage());
                    }
                    setDataTableViewBook(stmtQuery);
                }
            }catch (Exception e){
                System.out.println("error " + e.getMessage());
            }
        }

    }
    private void setTextField(BookModel k ){
        codeBookField.setText(k.getCode().getValue());
        categoryBookField.setText(k.getCategoryBook().getValue());
        nameBookField.setText(k.getNameBook().getValue());
        authorBookField.setText(k.getAuthorBook().getValue());
        totalBookField.setText(k.getTotalBook().getValue());
    }
//    ===============================SEARCHBOOOK===========================================
    // Hàm tìm tím quyển sách
    @FXML
    private void searchBook() {
        String find="";
        String temp="";
        if(!codeBookField.getText().equals("")&&nameBookField.getText().equals("")&&categoryBookField.getText().equals("")&&authorBookField.getText().equals("")){
            find=new chuanhoa().chuanhoaAll(codeBookField.getText());
            temp = "SELECT * FROM book WHERE code ='"+find+"'";
        }
        else  if(codeBookField.getText().equals("")&&!nameBookField.getText().equals("")&&categoryBookField.getText().equals("")&&authorBookField.getText().equals("")){
            find = new chuanhoa().chuanhoa1(nameBookField.getText());
            temp = "SELECT * FROM book WHERE name ='"+find+"'";
        }
        else  if(codeBookField.getText().equals("")&&nameBookField.getText().equals("")&&!categoryBookField.getText().equals("")&&authorBookField.getText().equals("")){
            find = new chuanhoa().chuanhoa1(categoryBookField.getText());
            temp = "SELECT * FROM book WHERE category ='"+find+"'";
        }else if(codeBookField.getText().equals("")&&nameBookField.getText().equals("")&&categoryBookField.getText().equals("")&&!authorBookField.getText().equals(""))
        {
            find = new chuanhoa().chuanhoaFirst(authorBookField.getText());
            temp = "SELECT * FROM book WHERE author ='"+find+"'";
        }
        if(find.equals("")){
            tableViewBook.setItems(setDataTableViewBook(stmtQuery));
        }
        else{
            tableViewBook.setItems( setDataTableViewBook(temp));
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

    ObservableList<BookModel> getFunctionSetTableViewBook() {
        return setDataTableViewBook(stmtQuery);
    }

    public void clear() {
       codeBookField.setText("");
        nameBookField.setText("");
        authorBookField.setText("");
        categoryBookField.setText("");
        totalBookField.setText("");
    }
}

